package com.lab516.base;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.util.StringUtils;

public class PageQuery extends BaseUtils {

	private static final Pattern PTN_NAME = Pattern.compile(":(\\w+)");

	private static final Pattern PTN_FROM = Pattern.compile("(?is)(\\s|\\))from(?:\\s|\\()");

	private static final Pattern PTN_ORDER = Pattern.compile("(?is)(?:\\s|\\))order\\s+by\\s");

	private EntityManager em;

	private Map<String, Comparable> paramMap;

	private StringBuilder query;

	public PageQuery(EntityManager em) {
		this.em = em;
		this.paramMap = new HashMap<String, Comparable>();
		this.query = new StringBuilder();
	}

	public void append(String queryPart) {
		query.append(" " + queryPart + " ");
	}

	public void whereNullableStartDay(String queryPart, Date param) {
		whereNullable(queryPart, param, getStartTimeOfDay(param));
	}

	public void whereNullableEndDay(String queryPart, Date param) {
		whereNullable(queryPart, param, getEndTimeOfDay(param));
	}

	public void whereNullableContains(String queryPart, Comparable param) {
		whereNullable(queryPart, param, "%" + param + "%");
	}

	public void whereNullable(String queryPart, Comparable param) {
		whereNullable(queryPart, param, param);
	}

	public void whereNullable(String queryPart, Comparable param, Comparable queryParam) {
		if (isNotEmpty(param)) {
			whereNotNull(queryPart, param, queryParam);
		}
	}

	public void whereNotNull(String queryPart, Comparable param) {
		whereNotNull(queryPart, param, param);
	}

	public void whereNotNullContains(String queryPart, Comparable param) {
		whereNotNull(queryPart, param, "%" + param + "%");
	}

	public void whereNotNull(String queryPart, Comparable param, Comparable queryParam) {
		append(queryPart);
		String paramName = getParamName(queryPart);
		paramMap.put(paramName, queryParam);
	}

	public void setParam(String paramName, Comparable paramVal) {
		paramMap.put(paramName, paramVal);
	}

	private String getParamName(String queryPart) {
		Matcher m = PTN_NAME.matcher(queryPart);
		m.find();
		return m.group(1);
	}

	public Page getPageBySql(int pageNo, int pageSize) {
		return getPage(pageNo, pageSize, null, true);
	}

	public <T> Page getPageBySql(int pageNo, int pageSize, Class<T> entity) {
		return getPage(pageNo, pageSize, entity, true);
	}

	public Page getPageByHql(int pageNo, int pageSize) {
		return getPage(pageNo, pageSize, null, false);
	}

	public <T> Page getPageByHql(int pageNo, int pageSize, Class<T> entity) {
		return getPage(pageNo, pageSize, entity, false);
	}

	private Page getPage(int pageNo, int pageSize, Class entity, boolean isNative) {
		String countQuery = convertToCountQuery(query.toString());
		long recordCount = getCount(countQuery, isNative);

		List recordList = getList(pageNo, pageSize, entity, isNative);

		return new Page(recordList, recordCount, pageSize, pageNo);
	}

	private String convertToCountQuery(String query) {
		String sql = convertSelectToCount(query);
		return removeOrderBy(sql).trim();
	}

	public long getCountByHql() {
		return getCount(false);
	}

	public long getCountBySql() {
		return getCount(true);
	}

	private long getCount(boolean isNative) {
		return getCount(query.toString(), isNative);
	}

	private long getCount(String query, boolean isNative) {
		Query emQry = isNative ? em.createNativeQuery(query) : em.createQuery(query);
		setQueryParams(emQry, paramMap);
		return Long.parseLong(emQry.getSingleResult().toString());
	}

	public static String convertSelectToCount(String query) {
		Matcher m = PTN_FROM.matcher(query);

		while (m.find()) {
			String part = query.substring(0, m.start()) + m.group(1);
			int lefBrakt = StringUtils.countOccurrencesOf(part, "(");
			int ritBrakt = StringUtils.countOccurrencesOf(part, ")");

			if (lefBrakt == ritBrakt) {
				query = query.substring(part.length());
				break;
			}
		}
		return "select count(*) " + query;
	}

	private String removeOrderBy(String sql) {
		Matcher m = PTN_ORDER.matcher(sql);

		while (m.find()) {
			String orderBy = sql.substring(m.end());

			if (!orderBy.contains(")")) {
				return sql.substring(0, m.start() + 1);
			}
		}
		return sql;
	}

	public List getListBySql(int pageNo, int pageSize) {
		return getList(pageNo, pageSize, null, true);
	}

	public <T> List<T> getListBySql(int pageNo, int pageSize, Class<T> entity) {
		return getList(pageNo, pageSize, entity, true);
	}

	public List getListByHql(int pageNo, int pageSize) {
		return getList(pageNo, pageSize, null, false);
	}

	public <T> List<T> getListByHql(int pageNo, int pageSize, Class<T> entity) {
		return getList(pageNo, pageSize, entity, false);
	}

	private List getList(int pageNo, int pageSize, Class entity, boolean isNative) {
		Query emQry = createQuery(query.toString(), entity, isNative);
		setQueryParams(emQry, paramMap);
		emQry.setFirstResult(computeStart(pageNo, pageSize));
		emQry.setMaxResults(pageSize);
		return emQry.getResultList();
	}

	private Query createQuery(String query, Class entity, boolean isNative) {
		if (entity != null && isNative) {
			return em.createNativeQuery(query, entity);
		}
		if (entity != null && !isNative) {
			return em.createQuery(query, entity);
		}
		if (entity == null && isNative) {
			return em.createNativeQuery(query);
		}
		if (entity == null && !isNative) {
			return em.createQuery(query);
		}
		throw new RuntimeException();
	}

	private void setQueryParams(Query query, Map<String, Comparable> paramMap) {
		for (Entry<String, Comparable> param : paramMap.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
	}

}
