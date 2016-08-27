package com.lab516.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据库操作Service基类
 * <p>
 * 自动生成了增、删、改、查(DAO操作) <br>
 * <p>
 * 继承该类时请一定加上实体类的泛型 eg: class UserService extends BaseService<User><br>
 */
public abstract class BaseService<T extends BaseEntity> extends BaseUtils {

	private Class<T> entityClazz;

	@PersistenceContext
	protected EntityManager em;

	public BaseService() {
		/**
		 * BaseService<T>的泛型T获取实体的class <br>
		 * eg: class UserService extends BaseService
		 * <User> 则下面的代码把entityClazz设为User.class
		 */
		Type genType = getClass().getGenericSuperclass();

		if (genType instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
			entityClazz = ((Class) params[0]);
		}
	}

	public T findById(Serializable id) {
		return em.find(entityClazz, id);
	}

	public List<T> findAll() {
		return em.createQuery("from " + entityClazz.getSimpleName()).getResultList();
	}

	@Transactional
	public T insert(T obj) {
		initID(obj);
		em.persist(obj);
		return obj;
	}

	@Transactional
	public void insertAll(T... objs) {
		for (T o : objs) {
			insert(o);
		}
	}

	@Transactional
	public T update(T obj) {
		initID(obj);
		em.merge(obj);
		return obj;
	}

	@Transactional
	public void delete(Serializable id) {
		T obj = em.find(entityClazz, id);
		em.remove(obj);
	}

	public JPAQuery createJPAQuery() {
		return new JPAQuery(em, entityClazz);
	}

	protected PageQuery createPageQuery() {
		return new PageQuery(em);
	}

	private void initID(T obj) {
		try {
			for (Field field : entityClazz.getDeclaredFields()) {
				boolean isId = (field.getAnnotation(Id.class) != null);
				boolean isString = field.getType().equals(String.class);

				if (isId && isString) {
					String idVal = BeanUtils.getProperty(obj, field.getName());

					if (BaseUtils.isEmpty(idVal)) {
						BeanUtils.setProperty(obj, field.getName(), BaseUtils.getUUID());
					}
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

}
