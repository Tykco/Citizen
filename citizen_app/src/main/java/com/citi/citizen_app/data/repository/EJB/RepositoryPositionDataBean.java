package com.citi.citizen_app.data.repository.EJB;

import java.math.BigDecimal;
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
	
	public List<Position> getPositionsByPortfolioId(int portfolioId) {
		TypedQuery<Position> query = entityManager.createQuery("SELECT p FROM Portfolio AS p WHERE p.portfolio.portfolioId LIKE '%" + portfolioId + "%'", Position.class);
		List<Position> positionList = query.getResultList();
		return positionList;
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
}
