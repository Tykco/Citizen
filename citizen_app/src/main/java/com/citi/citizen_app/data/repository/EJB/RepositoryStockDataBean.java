package com.citi.citizen_app.data.repository.EJB;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.citi.citizen_app.model.Stock;

@Stateless
public class RepositoryStockDataBean {
	@Inject
	private EntityManager entityManager;
	
	public Stock getStockByTicker(String ticker) {
		TypedQuery<Stock> query = entityManager.createQuery(
				"SELECT s FROM Stock AS s WHERE s.ticker LIKE '%" + ticker + "%' ",Stock.class);
		return query.getSingleResult();
	}
	
	public int getStockIdByTicker(String ticker) {
		TypedQuery<Stock> query = entityManager.createQuery(
				"SELECT s FROM Stock AS s WHERE s.ticker LIKE '%" + ticker + "%' ",Stock.class);
		int stockId = query.getSingleResult().getStockId();
		return stockId;
	}
}
