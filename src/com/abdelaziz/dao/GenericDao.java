package com.abdelaziz.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {

	public void create(T entity);

	public void update(T entity);

	public T findById(ID id);

	public List<T> findAll();

	public void delete(T entity);

	public void deleteById(ID id);
}