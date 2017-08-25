package com.jfeesoft.kindergarten.view.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.jfeesoft.kindergarten.model.GenericEntity;
import com.jfeesoft.kindergarten.service.GenericService;
import com.jfeesoft.kindergarten.view.utils.ProperSortOrder;

public abstract class GenericLazyDataModel<T extends GenericEntity> extends LazyDataModel<T> {

	private static final long serialVersionUID = 1L;

	private final GenericService genericService;

	public GenericLazyDataModel(GenericService genericService) {
		super();
		this.genericService = genericService;
	}

	@SuppressWarnings("unchecked")
	public T getRowData(String rowKey) {
		ArrayList<T> entities = ((ArrayList<T>) this.getWrappedData());
		for (T entity : entities) {
			if (entity.getId().longValue() == Integer.parseInt(rowKey)) {
				return entity;
			}
		}
		return null;
	}

	@Override
	public Object getRowKey(T entity) {
		return entity.getId();
	}

	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		List<T> entities = genericService.load(first, pageSize, sortField, ProperSortOrder.getDirection(sortOrder),
				filters);
		Long count = genericService.countRepositoryFilter(filters);
		this.setRowCount(count.intValue());
		this.setWrappedData(entities);
		return entities;
	}
}
