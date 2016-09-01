package com.citi.citizen_app.data.repository.EJB;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.citi.citizen_app.model.Livemarketdata;
import com.citi.citizen_app.model.Stock;

@Stateless
public class RepositoryLiveDataBean{

	@Inject
	private EntityManager entityManager;

	public List<Livemarketdata> getLiveDataByTicker(String ticker) {

		TypedQuery<Livemarketdata> query = entityManager.createQuery(
				"SELECT l FROM Livemarketdata AS l WHERE l.stock.ticker LIKE '%" + ticker + "%'", Livemarketdata.class);
		List<Livemarketdata> liveDataList = query.getResultList();
	
		return liveDataList;
	}

	public List<Livemarketdata> getLiveDataByTickerIdConstraint(String ticker, int tableId) {

		TypedQuery<Livemarketdata> query = entityManager.createQuery(
				"SELECT l FROM Livemarketdata AS l WHERE l.stock.ticker LIKE '%" + ticker + "%' AND l.id > :tableId", Livemarketdata.class);
		query.setParameter("tableId", tableId);

		List<Livemarketdata> liveDataList = query.getResultList();
		return liveDataList;
	}

	public Stock getStockIdByTicker(String ticker) {
		TypedQuery<Stock> query = entityManager.createQuery(
				"SELECT s FROM Stock AS s WHERE s.ticker LIKE '%" + ticker + "%'", Stock.class);
		Stock stock = query.getSingleResult();
		return stock;
	}
	
	//Can retrieve price from here.
	public Livemarketdata getLivemarketdataById(int liveId) {
		TypedQuery<Livemarketdata> query = entityManager.createQuery(
				"SELECT l FROM Livemarketdata AS l WHERE l.liveId = :liveId", Livemarketdata.class);
		query.setParameter("liveId", liveId);
		Livemarketdata liveData = query.getSingleResult();
		return liveData;
	}
	
	public void persistLiveData(String liveDataString) {
		Livemarketdata liveMarketData = new Livemarketdata();

		String[] liveData = liveDataString.split(",");
		liveMarketData.setStock(getStockIdByTicker(liveData[0]));;
		liveMarketData.setOpen(Float.parseFloat(liveData[1]));
		liveMarketData.setPrice(Float.parseFloat(liveData[2]));
		liveMarketData.setAsk(Float.parseFloat(liveData[3]));
		liveMarketData.setBid(Float.parseFloat(liveData[4]));

		entityManager.persist(liveMarketData);
		entityManager.flush();
	}

	public List<Livemarketdata> getLiveData() {
		TypedQuery<Livemarketdata> query = entityManager.createQuery("SELECT l from Livemarketdata AS l", Livemarketdata.class);
		List<Livemarketdata> liveDataList = query.getResultList();
		
		/*for (int i =0; i< liveDataList.size(); i++) {
			Livemarketdata l = liveDataList.get(i);
			System.out.println(l.getLiveId());
			System.out.println(l.getStock().getTicker());
			
		}*/
		System.out.println("size of shit: " + liveDataList.size());
		return liveDataList;
	}
}
