package com.citi.citizen_app.data.repository.EJB;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.citi.citizen_app.model.Position;

@Stateless
public class RepositoryPositionDataBean {

	@Inject
	private EntityManager entityManager;
	@Inject
	private RepositoryPortfolioDataBean portfolioDataBean;
	@Inject
	private RepositoryStockDataBean stockDataBean;
	
	public List<Position> getPositionsByPortfolioId(int portfolioId) {
		TypedQuery<Position> query = entityManager.createQuery("SELECT p FROM Position AS p WHERE p.portfolio.portfolioId = :portfolioId ", Position.class);
		query.setParameter("portfolioId", portfolioId);
		List<Position> positionList = query.getResultList();
		return positionList;
	}
	
	public List<Integer> getPositionIdsByPortfolioId(int portfolioId) {
		TypedQuery<Position> query = entityManager.createQuery(
				"SELECT p FROM Position AS p WHERE p.portfolio.portfolioId = :portfolioId", Position.class);
		query.setParameter("portfolioId", portfolioId);
		List<Position> positionList = query.getResultList();
		List<Integer> positionIdList = new ArrayList<Integer>();
		for (Position p : positionList)
			positionIdList.add(p.getPositionId());
		return positionIdList;
	}
	
	/**
	 * If shares is sold, int sharesBoughtSold will be negative.
	 * @param positionId
	 * @param sharesBoughtSold
	 */
	public void updateShares(int positionId, int sharesBoughtSold) {
		Position position = entityManager.find(Position.class, positionId);
		position.setShares(position.getShares() + sharesBoughtSold);
		entityManager.merge(position);
		entityManager.flush();
	}
	
	public void updatePositionsProfitLoss(int positionId, BigDecimal positionProfitLoss) {
		Position position = entityManager.find(Position.class, positionId);
		position.setSharesProfitLoss(positionProfitLoss);
		entityManager.merge(position);
		entityManager.flush();
	}

	public void persistPosition(int portfolioId, String ticker) {
		Position position = new Position();
		
		position.setPortfolio(portfolioDataBean.getPortfolioById(portfolioId).get(0));
		position.setStock(stockDataBean.getStockByTicker(ticker));
		position.setSharesProfitLoss(BigDecimal.ZERO);
		
		entityManager.persist(position);
	}
}
