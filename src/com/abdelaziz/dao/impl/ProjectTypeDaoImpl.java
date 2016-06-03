package com.abdelaziz.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.abdelaziz.dao.ProjectTypeDao;
import com.abdelaziz.model.ProjectType;

public class ProjectTypeDaoImpl extends GenericDaoImpl<ProjectType, Long>
		implements ProjectTypeDao {

	private static final long serialVersionUID = 1L;

	@Transactional(readOnly = true)
	@Override
	public ProjectType findByName(String name) {
		if (name == null || name.equals("")) {
			return null;
		}
		Criteria criteria = getCurrentSession().createCriteria(
				ProjectType.class);
		criteria.add(
				Restrictions.ilike("projectTypeLabel", name, MatchMode.EXACT))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (ProjectType) criteria.uniqueResult();
	}
}
