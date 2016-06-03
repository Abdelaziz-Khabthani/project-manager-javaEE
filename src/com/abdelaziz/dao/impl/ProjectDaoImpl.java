package com.abdelaziz.dao.impl;

import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import com.abdelaziz.dao.ProjectDao;
import com.abdelaziz.dao.ProjectTypeDao;
import com.abdelaziz.model.Project;
import com.abdelaziz.model.ProjectType;

public class ProjectDaoImpl extends GenericDaoImpl<Project, Long> implements
		ProjectDao {

	private static final long serialVersionUID = 1L;
	private ProjectTypeDao projectTypeDao;

	public void setProjectTypeDao(ProjectTypeDao projectTypeDao) {
		this.projectTypeDao = projectTypeDao;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Project> getCurrentProjects() {
		Criteria criteria = getCurrentSession().createCriteria(Project.class);
		criteria.add(Restrictions.ge("projectEndDate", new Date()))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Project>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Project> findByName(String name, boolean onlyLiveProjects) {
		Criteria criteria = getCurrentSession().createCriteria(Project.class);
		criteria.add(Restrictions
				.ilike("projectName", name, MatchMode.ANYWHERE));
		if (onlyLiveProjects)
			criteria.add(Restrictions.ge("projectEndDate", new Date()));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Project>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Project> findByStartDate(Date date, boolean onlyLiveProjects) {
		Criteria criteria = getCurrentSession().createCriteria(Project.class);
		criteria.add(Restrictions.eq("projectStartDate", date));
		if (onlyLiveProjects)
			criteria.add(Restrictions.ge("projectEndDate", new Date()));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Project>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Project> findByEndDate(Date date, boolean onlyLiveProjects) {
		Criteria criteria = getCurrentSession().createCriteria(Project.class);
		criteria.add(Restrictions.eq("projectEndDate", date));
		if (onlyLiveProjects)
			criteria.add(Restrictions.ge("projectEndDate", new Date()));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Project>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Project> findByProjectTypeLabel(String label,
			boolean onlyLiveProjects) {
		if (projectTypeDao.findByName(label) == null) {
			return null;
		} else {
			ProjectType projectType = projectTypeDao.findByName(label);
			Criteria criteria = getCurrentSession().createCriteria(
					Project.class);
			criteria.add(Restrictions.eq("projectType", projectType));
			if (onlyLiveProjects)
				criteria.add(Restrictions.ge("projectEndDate", new Date()));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return (List<Project>) criteria.list();
		}
	}
}
