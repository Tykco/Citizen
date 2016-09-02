package com.citi.citizen_app.data.repository.EJB;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.citi.citizen_app.model.Trade;

@Stateless
public class RepositoryTradeDataBean {

	@Inject
	private EntityManager entityManager;
	@Inject
	private RepositoryStockDataBean stockBean;
	@Inject
	private RepositoryPortfolioDataBean portfolioBean;

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

	public void insertTradePreApproval(String ticker, int portfolioId, String buyOrSell, float price,
			int sharesBoughtSold, String strategy, String status) {

		Trade trade = new Trade();
		trade.setStock(stockBean.getStockByTicker(ticker));
		trade.setPortfolio(portfolioBean.getPortfolioById(portfolioId).get(0));
		trade.setBuySell(buyOrSell);
		trade.setPrice(price);
		trade.setQuantity(sharesBoughtSold);
		trade.setStrategy(strategy);
		trade.setApproved("PENDING");
		
		entityManager.persist(trade);
	}
	
	public int getLastTradeId() {
		TypedQuery<Trade> query = entityManager.createQuery(
				"SELECT t FROM Trade AS t order by t.id desc", Trade.class);
		
		List<Trade> tradeList = query.setMaxResults(1).getResultList();
		int tradeId = tradeList.get(0).getTradeId();
		return tradeId;
	}

	public List<Trade> getTradeByPortfolioId(int portfolioId) {
		TypedQuery<Trade> query = entityManager.createQuery(
				"SELECT t FROM Trade AS t WHERE t.portfolio.portfolioId = :portfolioId", Trade.class);
		query.setParameter("portfolioId", portfolioId);
		return query.getResultList();
	}
}
