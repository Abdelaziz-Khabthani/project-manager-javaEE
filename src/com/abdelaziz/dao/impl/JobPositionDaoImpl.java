package com.abdelaziz.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.abdelaziz.dao.JobPositionDao;
import com.abdelaziz.model.JobPosition;

public class JobPositionDaoImpl extends GenericDaoImpl<JobPosition, Long>
		implements JobPositionDao {

	private static final long serialVersionUID = 1L;

	@Transactional(readOnly = true)
	@Override
	public JobPosition findByName(String name) {
		if (name == null || name.equals("")) {
			return null;
		}
		Criteria criteria = getCurrentSession().createCriteria(
				JobPosition.class);
		criteria.add(
				Restrictions.ilike("jobPositonLabel", name, MatchMode.EXACT))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (JobPosition) criteria.uniqueResult();
	}
}
