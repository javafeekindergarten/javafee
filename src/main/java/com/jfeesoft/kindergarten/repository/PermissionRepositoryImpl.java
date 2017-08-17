package com.jfeesoft.kindergarten.repository;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Sort.Direction;

import com.jfeesoft.kindergarten.model.Permission;

public class PermissionRepositoryImpl implements PermissionRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Long countPermissionRepositoryFilter(Map<String, Object> filters) {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Permission.class, "permission");

		// createQuery(criteria);
		addWhereCriteria(criteria, filters);

		criteria.setProjection(Projections.countDistinct("permission.id"));
		return (Long) criteria.uniqueResult();
	}

	@Override
	public List<Permission> findPermissionRepositorySortFilterPage(int first, int pageSize, String sortField,
			Direction sortOrder, Map<String, Object> filters) {
		Session session = em.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Permission.class, "permission");
		// createQuery(criteria);
		addWhereCriteria(criteria, filters);

		if (sortField != null && sortOrder != null) {
			if (sortOrder.equals(Direction.ASC)) {
				criteria.addOrder(Order.asc(sortField));
			} else {
				criteria.addOrder(Order.desc(sortField));
			}
		} else {
			criteria.addOrder(Order.desc("permission.id"));
		}
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);

		return criteria.list();
	}

	/*
	 * private void createQuery(Criteria criteria) {
	 * criteria.setFetchMode("smsUzytkownik", FetchMode.JOIN);
	 * criteria.setFetchMode("smsStatus", FetchMode.JOIN);
	 * criteria.setFetchMode("smsUzytkownik.puUzytkownik", FetchMode.JOIN);
	 * criteria.createAlias("smsUzytkownik.puUzytkownik", "puUzytkownik");
	 * criteria.createAlias("smsStatus", "smsStatus");
	 * criteria.createAlias("smsUzytkownik", "smsUzytkownik");
	 * criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); }
	 */

	private void addWhereCriteria(Criteria criteria, Map<String, Object> filters) {
		if (filters != null && !filters.isEmpty()) {
			for (Entry<String, Object> entry : filters.entrySet()) {
				if (entry.getValue() instanceof String) {
					criteria.add(Restrictions.like(entry.getKey(), (String) entry.getValue(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.ge(entry.getKey(), entry.getValue()));
				}
			}
		}
	}

}
