package com.citi.citizen_app.data.repository.EJB;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.citi.citizen_app.model.Position;
import com.citi.citizen_app.model.Trade;

@Stateless
public class RepositoryTradeDataBean {

	@Inject
	private EntityManager entityManager;

	public List<Trade> getTradesByPortfolioId(int portfolioId) {

		TypedQuery<Trade> query = entityManager.createQuery(
				"SELECT t FROM Trade AS t WHERE t.portfolio.portfolioId LIKE '%" + portfolioId + "%'", Trade.class);
		List<Trade> tradesList = query.getResultList();
		return tradesList;
	}
	
	public List<Trade> getTradesByPosition(int portfolioId, int stockId) {
		TypedQuery<Trade> query = entityManager.createQuery(
				"SELECT t FROM Trade AS t WHERE t.portfolio.portfolioId LIKE '%" + portfolioId + "%' "
						+ "AND t.stock.stockId LIKE '%" + stockId + "%'", Trade.class);
		List<Trade> tradesList = query.getResultList();
		return tradesList;
	}
	
	public void updateApprovedTrade(int tradeId) {
		Trade trade = entityManager.find(Trade.class, tradeId);
		trade.setApproved("APPROVED");
		entityManager.merge(trade);
		entityManager.flush();
	}
}
