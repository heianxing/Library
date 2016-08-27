package com.lab516.base;

import static com.lab516.base.BaseUtils.calcPageCount;
import static com.lab516.base.BaseUtils.calcStart;
import static com.lab516.base.BaseUtils.getEndTimeOfDay;
import static com.lab516.base.BaseUtils.getStartTimeOfDay;
import static com.lab516.base.BaseUtils.isEmpty;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * JPA动态查询语句
 * <p>
 * 查询参数可以为空或者不空 <br>
 * 如果查询参数为空则可以忽略其所在的where子句 <br>
 * 获取count, list, 或者分页数据Page <br>
 * <p>
 * 所有whereNullableXXX(property, param)则param为空则忽略此where子句<br>
 * 所有whereNotNullXXX(property, param)则此where子句无法被忽略，即使param为空<br>
 */
public class JPAQuery {

	private EntityManager em;

	private CriteriaBuilder criteriaBuilder;

	private CriteriaQuery criteriaQuery;

	private Root root;

	private List<Predicate> wheres;

	private List<Order> orders;

	protected JPAQuery(EntityManager em, Class clazz) {
		this.em = em;
		this.criteriaBuilder = em.getCriteriaBuilder();
		this.wheres = new LinkedList<Predicate>();
		this.orders = new LinkedList<Order>();

		from(clazz);
	}

	public Page getPage(int pageNo, int pageSize) {
		// 查询Count
		combineWheres();
		criteriaQuery.select(criteriaBuilder.count(root));
		Query countQuery = em.createQuery(criteriaQuery);
		long recordCount = Long.parseLong(em.createQuery(criteriaQuery).getSingleResult().toString());

		// 计算一共有多少页 --> 当前应该是第几页 --> 第一条数据的行号
		int pageCount = calcPageCount(pageSize, recordCount);
		pageNo = Math.min(pageCount, pageNo);
		int start = calcStart(pageNo, pageSize);

		// 查询List
		combineOrders();
		criteriaQuery.select(root);
		Query listQuery = em.createQuery(criteriaQuery);
		listQuery.setFirstResult(start);
		listQuery.setMaxResults(pageSize);
		List recordList = listQuery.getResultList();

		return new Page(recordList, recordCount, pageSize, pageNo, pageCount);
	}

	/** 查询数据条数 */
	public long getRecordCount() {
		combineWheres();
		criteriaQuery.select(criteriaBuilder.count(root));
		return Long.parseLong(em.createQuery(criteriaQuery).getSingleResult().toString());
	}

	/** 查询数据List */
	public List getResultList() {
		combineWheres();
		combineOrders();
		criteriaQuery.select(root);
		return em.createQuery(criteriaQuery).getResultList();
	}

	public List getResultList(int pageNo, int pageSize) {
		combineWheres();
		combineOrders();
		criteriaQuery.select(root);
		int start = calcStart(pageNo, pageSize);
		Query query = em.createQuery(criteriaQuery);
		query.setFirstResult(start);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	private void from(Class clazz) {
		criteriaQuery = criteriaBuilder.createQuery(clazz);
		root = criteriaQuery.from(clazz);
	}

	/** Where子句 ">", 如果参数为空则忽略此子句 */
	public void whereNullableGreaterThan(String propertyName, Comparable param) {
		if (isParamValid(param)) {
			whereNotNullGreaterThan(propertyName, param);
		}
	}

	/** Where子句 ">", 此子句无法忽略 */
	public void whereNotNullGreaterThan(String propertyName, Comparable param) {
		Path property = root.get(propertyName);
		Predicate where = criteriaBuilder.greaterThan(property, param);
		wheres.add(where);
	}

	/** Where子句 ">=", 如果参数为空则忽略此子句 */
	public void whereNullableGreaterOrEqual(String propertyName, Comparable param) {
		if (isParamValid(param)) {
			whereNotNullGreaterOrEqual(propertyName, param);
		}
	}

	/** Where子句 ">=", 此子句无法忽略 */
	public void whereNotNullGreaterOrEqual(String propertyName, Comparable param) {
		Path property = root.get(propertyName);
		Predicate where = criteriaBuilder.greaterThanOrEqualTo(property, param);
		wheres.add(where);
	}

	/** Where子句 "<", 如果参数为空则忽略此子句 */
	public void whereNullableLessThan(String propertyName, Comparable param) {
		if (isParamValid(param)) {
			whereNotNullLessThan(propertyName, param);
		}
	}

	/** Where子句 "<", 此子句无法忽略 */
	public void whereNotNullLessThan(String propertyName, Comparable param) {
		Path property = root.get(propertyName);
		Predicate where = criteriaBuilder.lessThan(property, param);
		wheres.add(where);
	}

	/** Where子句 "<=", 如果参数为空则忽略此子句 */
	public void whereNullableLessOrEqual(String propertyName, Comparable param) {
		if (isParamValid(param)) {
			whereNotNullLessOrEqual(propertyName, param);
		}
	}

	/** Where子句 "<=", 此子句无法忽略 */
	public void whereNotNullLessOrEqual(String propertyName, Comparable param) {
		Path property = root.get(propertyName);
		Predicate where = criteriaBuilder.lessThanOrEqualTo(property, param);
		wheres.add(where);
	}

	/** Where子句 "=", 如果参数为空则忽略此子句 */
	public void whereNullableEqual(String propertyName, Comparable param) {
		if (isParamValid(param)) {
			whereNotNullEqual(propertyName, param);
		}
	}

	public void whereNullableStartDay(String propertyName, Date start) {
		if (start != null) {
			whereNotNullGreaterOrEqual(propertyName, getStartTimeOfDay(start));
		}
	}

	public void whereNullableEndDay(String propertyName, Date end) {
		if (end != null) {
			whereNotNullLessOrEqual(propertyName, getEndTimeOfDay(end));
		}
	}

	/** Where子句 "=", 此子句无法忽略 */
	public void whereNotNullEqual(String propertyName, Comparable param) {
		Path property = root.get(propertyName);
		Predicate where = criteriaBuilder.equal(property, param);
		wheres.add(where);
	}

	/** Where子句 "!=", 如果参数为空则忽略此子句 */
	public void whereNullableNotEqual(String propertyName, Comparable param) {
		if (isParamValid(param)) {
			whereNotNullNotEqual(propertyName, param);
		}
	}

	/** Where子句 "!=", 此子句无法忽略 */
	public void whereNotNullNotEqual(String propertyName, Comparable param) {
		Path property = root.get(propertyName);
		Predicate where = criteriaBuilder.notEqual(property, param);
		wheres.add(where);
	}

	/** Where子句 "like %param%", 如果参数为空则忽略此子句 */
	public void whereNullableContains(String propertyName, String param) {
		if (isParamValid(param)) {
			whereNotNullContains(propertyName, param);
		}
	}

	/** Where子句 "like %param%", 此子句无法忽略 */
	public void whereNotNullContains(String propertyName, String param) {
		whereNotNullLike(propertyName, "%" + param + "%");
	}

	/** Where子句 "like param%", 如果参数为空则忽略此子句 */
	public void whereNullableStartWith(String propertyName, String param) {
		if (isParamValid(param)) {
			whereNotNullStartWith(propertyName, param);
		}
	}

	/** Where子句 "like param%", 此子句无法忽略 */
	public void whereNotNullStartWith(String propertyName, String param) {
		whereNotNullLike(propertyName, param + "%");
	}

	/** Where子句 "like %param", 如果参数为空则忽略此子句 */
	public void whereNullableEndWith(String propertyName, String param) {
		if (isParamValid(param)) {
			whereNotNullEndWith(propertyName, param);
		}
	}

	/** Where子句 "like %param", 此子句无法忽略 */
	public void whereNotNullEndWith(String propertyName, String param) {
		whereNotNullLike(propertyName, "%" + param);
	}

	/** Where子句 "like", 此子句无法忽略 */
	public void whereNotNullLike(String propertyName, String param) {
		Path property = root.get(propertyName);
		Predicate where = criteriaBuilder.like(property, param);
		wheres.add(where);
	}

	public void orderByDesc(String propertyName) {
		Order order = criteriaBuilder.desc(root.get(propertyName));
		orders.add(order);
	}

	public void orderByAsc(String propertyName) {
		Order order = criteriaBuilder.asc(root.get(propertyName));
		orders.add(order);
	}

	private void combineWheres() {
		Predicate[] wheresArr = wheres.toArray(new Predicate[0]);
		criteriaQuery.where(wheresArr);
	}

	private void combineOrders() {
		Order[] orderArr = orders.toArray(new Order[0]);
		criteriaQuery.orderBy(orderArr);
	}

	/**
	 * 判断对象是否为非空<br>
	 * 对象为null或空字符串则认为其为空
	 */
	private Boolean isParamValid(Object param) {
		if (param instanceof String) {
			return !isEmpty((String) param);
		} else {
			return (param != null);
		}
	}

}
