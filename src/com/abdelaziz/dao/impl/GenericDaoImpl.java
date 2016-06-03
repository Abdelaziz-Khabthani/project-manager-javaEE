package com.abdelaziz.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.abdelaziz.dao.GenericDao;

public abstract class GenericDaoImpl<T, ID extends Serializable> implements
		GenericDao<T, ID>, Serializable {

	private static final long serialVersionUID = 1L;

	private SessionFactory sessionFactory;

	private Class<T> type;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			throw new IllegalStateException(
					"SessionFactory has not been set on DAO before usage");
		return sessionFactory;
	}

	public Class<T> getType() {
		return type;
	}

	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
		this.type = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Transactional
	@Override
	public void create(T entity) {
		getCurrentSession().persist(entity);
	}

	@Transactional
	@Override
	public void update(T entity) {
		getCurrentSession().update(entity);

	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public T findById(ID id) {
		return (T) getCurrentSession().get(getType(), id);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<T> findAll() {
		return (List<T>) getCurrentSession().createQuery(
				"from " + getType().getName()).list();
	}

	@Transactional
	@Override
	public void delete(T entity) {
		getCurrentSession().delete(entity);
	}

	@Transactional
	@Override
	public void deleteById(ID id) {
		getCurrentSession().delete(findById(id));
	}

	protected final Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
}