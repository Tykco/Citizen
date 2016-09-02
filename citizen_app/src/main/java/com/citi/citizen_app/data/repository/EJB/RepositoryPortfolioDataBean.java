package com.citi.citizen_app.data.repository.EJB;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.citi.citizen_app.model.Portfolio;

@Stateless
public class RepositoryPortfolioDataBean {

	@Inject
	private EntityManager entityManager;
	@Inject RepositoryUserDataBean userBean;

	public List<Portfolio> getPortfoliosByUsername(String username) {

		TypedQuery<Portfolio> query = entityManager.createQuery(
				"SELECT p FROM Portfolio AS p WHERE p.user.username = :username", Portfolio.class);
		query.setParameter("username", username);
		List<Portfolio> portfolioList = query.getResultList();
		return portfolioList;
	}
	
	public void updatePortfolioBalProfitLoss(int portfolioId, BigDecimal pfProfitLoss) {

		Portfolio portfolio = entityManager.find(Portfolio.class, portfolioId);
		portfolio.setStartBalance(portfolio.getStartBalance().add(pfProfitLoss));
		portfolio.setPfProfitLoss(portfolio.getPfProfitLoss().add(pfProfitLoss));
		entityManager.merge(portfolio);
		entityManager.flush();
	}

	public List<Portfolio> getPortfolioById(int portfolioId) {
		TypedQuery<Portfolio> query = entityManager.createQuery(
				"SELECT p FROM Portfolio p WHERE p.user.userId = :portfolioId",Portfolio.class);
		query.setParameter("portfolioId", portfolioId);
		return query.getResultList();
	}

	public void persistPortfolio(int userId) {
		Portfolio portfolio = new Portfolio();
		portfolio.setUser(userBean.getUserById(userId));
		portfolio.setStartBalance(new BigDecimal(500000));
		portfolio.setPfProfitLoss(new BigDecimal(0));
		entityManager.persist(portfolio);
		entityManager.flush();
	}

	public void setBalance(BigDecimal bigDecimal) {
		Portfolio portfolio = entityManager.find(Portfolio.class, 1);
		portfolio.setStartBalance(bigDecimal);
	}
}
