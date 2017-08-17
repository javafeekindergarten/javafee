package com.jfeesoft.kindergarten.service;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

public abstract class GenericServiceImpl<T, K extends Serializable> implements GenericService<T, K> {
	protected CrudRepository<T, K> repository;

	public GenericServiceImpl(CrudRepository<T, K> repository) {
		this.repository = repository;
	}

	public T save(T entity) {
		return repository.save(entity);
	}

	public void delete(T entity) {
		repository.delete(entity);
	}

}
