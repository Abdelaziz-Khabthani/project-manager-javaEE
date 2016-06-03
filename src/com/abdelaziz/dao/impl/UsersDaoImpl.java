package com.abdelaziz.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import com.abdelaziz.dao.UsersDao;
import com.abdelaziz.model.Users;

public class UsersDaoImpl extends GenericDaoImpl<Users, Long> implements
		UsersDao {

	private static final long serialVersionUID = 1L;

	@Transactional(readOnly = true)
	@Override
	public Users findUserByUsername(String username) {
		if (username == null || username.equals("")) {
			return null;
		}
		Criteria criteria = getCurrentSession().createCriteria(Users.class);
		criteria.add(Restrictions.like("usersName", username, MatchMode.EXACT))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Users) criteria.uniqueResult();
	}
}
