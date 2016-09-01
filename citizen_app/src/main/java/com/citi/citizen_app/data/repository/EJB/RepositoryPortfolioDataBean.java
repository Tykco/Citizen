package com.citi.citizen_app.data.repository.EJB;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.citi.citizen_app.model.Portfolio;

@Stateless
public class RepositoryPortfolioDataBean {

	@Inject
	private EntityManager entityManager;

	public Portfolio getPortfolioByUsername(String username) {

		TypedQuery<Portfolio> query = entityManager.createQuery(
				"SELECT p FROM Portfolio AS p WHERE p.user.username LIKE '%" + username + "%'", Portfolio.class);
		Portfolio portfolio = query.getSingleResult();
		return portfolio;
	}
	
	public void updatePortfolioBalProfitLoss(int portfolioId, BigDecimal pfProfitLoss) {

		Portfolio portfolio = entityManager.find(Portfolio.class, portfolioId);
		portfolio.setStartBalance(portfolio.getStartBalance().add(pfProfitLoss));
		portfolio.setPfProfitLoss(portfolio.getPfProfitLoss().add(pfProfitLoss));
		entityManager.merge(portfolio);
		entityManager.flush();
	}
}
