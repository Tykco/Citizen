package com.citi.citizen_app.data.repository.EJB;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.citi.citizen_app.model.Historicalmarketdata;

@Stateless
public class RepositoryHistDataBean {

	@Inject
	private EntityManager entityManager;

	public List<Historicalmarketdata> getHistDataDayByTicker(String ticker) {
		TypedQuery<Historicalmarketdata> query = entityManager.createQuery(
    			"SELECT h FROM Historicalmarketdata AS h WHERE h.stock.ticker LIKE '%" + ticker + "%' AND h.epochTimestamp IS NOT NULL", Historicalmarketdata.class);
		List<Historicalmarketdata> histDataList = query.getResultList();
    	return histDataList;
	}

	public List<Historicalmarketdata> getHistDataMonthYearByTicker(String ticker) {
		TypedQuery<Historicalmarketdata> query = entityManager.createQuery(
    			"SELECT h FROM Historicalmarketdata AS h WHERE h.stock.ticker LIKE '%" + ticker + "%' AND h.date IS NOT NULL", Historicalmarketdata.class);
		List<Historicalmarketdata> histDataList = query.getResultList();
    	return histDataList;
	}
}
