package com.citi.citizen_app.data.repository.EJB;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.citi.citizen_app.model.User;

@Stateless
public class RepositoryUserDataBean {
	@Inject
	private EntityManager entityManager;
	
	public User getUserById(int userId) {
		
		TypedQuery<User> query = entityManager.createQuery(
				"SELECT u FROM User AS u WHERE u.userId = :userId", User.class);
		query.setParameter("userId", userId);
		return query.getSingleResult();
		
	}
}
