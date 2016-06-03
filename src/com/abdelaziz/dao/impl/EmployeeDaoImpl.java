package com.abdelaziz.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.abdelaziz.dao.EmployeeDao;
import com.abdelaziz.dao.JobPositionDao;
import com.abdelaziz.model.Employee;
import com.abdelaziz.model.JobPosition;

public class EmployeeDaoImpl extends GenericDaoImpl<Employee, String> implements
		EmployeeDao {

	private static final long serialVersionUID = 1L;
	private JobPositionDao jobPositionDao;

	public void setJobPositionDao(JobPositionDao jobPositionDao) {
		this.jobPositionDao = jobPositionDao;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Employee> findByName(String name) {
		Criteria criteria = getCurrentSession().createCriteria(Employee.class);
		criteria.add(
				Restrictions.ilike("employeeName", name, MatchMode.ANYWHERE))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Employee>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Employee> findByBirthDate(Date date) {
		Criteria criteria = getCurrentSession().createCriteria(Employee.class);
		criteria.add(Restrictions.eq("employeeBirthdate", date))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Employee>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Employee> findByJobPositionLabel(String label) {
		if (jobPositionDao.findByName(label) == null) {
			return null;
		} else {
			JobPosition jobPosition = jobPositionDao.findByName(label);
			Criteria criteria = getCurrentSession().createCriteria(
					Employee.class);
			criteria.add(Restrictions.eq("jobPosition", jobPosition))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return (List<Employee>) criteria.list();
		}
	}
}