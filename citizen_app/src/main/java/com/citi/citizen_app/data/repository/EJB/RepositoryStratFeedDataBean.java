package com.citi.citizen_app.data.repository.EJB;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.citi.citizen_app.model.Stratfeed;

@Stateless
public class RepositoryStratFeedDataBean {

	@Inject
	private EntityManager entityManager;
	@Inject
	private RepositoryLiveDataBean liveDataBean;
	
	public List<Stratfeed> getStratFeedByTickerStrategyLiveId(String ticker, String strategy, int liveId) {

		TypedQuery<Stratfeed> query = entityManager.createQuery(
				"SELECT sf FROM Stratfeed AS sf WHERE sf.livemarketdata.liveId > :liveId AND sf.strategy LIKE '%" + strategy + "%' AND sf.livemarketdata.stock.ticker LIKE '%" + ticker + "%'", Stratfeed.class);
				//"SELECT sf, l.price FROM Stratfeed sf, Livemarketdata l WHERE sf.liveId LIKE '%" + liveId + "%'", Stratfeed.class);
		query.setParameter("liveId", liveId);
		List<Stratfeed> stratFeedList = query.getResultList();
		return stratFeedList;
	}
	
	public List<Stratfeed> getStratFeedByLiveId(int liveId) {

		TypedQuery<Stratfeed> query = entityManager.createQuery(
				"SELECT sf FROM Stratfeed AS sf WHERE sf.livemarketdata.liveId = :liveId", Stratfeed.class);
				//"SELECT sf, l.price FROM Stratfeed sf, Livemarketdata l WHERE sf.liveId LIKE '%" + liveId + "%'", Stratfeed.class);
		query.setParameter("liveId", liveId);
		List<Stratfeed> stratFeedList = query.getResultList();
		return stratFeedList;
	}

	
	//******************** PERSISTENCE METHODS FOR BOLLINGER BAND STRATEGY************************************
	
	public void persistStratFeedBbDataPrice(int liveId) {
		Stratfeed stratfeed = new Stratfeed();
		
		stratfeed.setStrategy("BB");
		stratfeed.setLivemarketdata(liveDataBean.getLivemarketdataById(liveId));
		
		entityManager.persist(stratfeed);
		entityManager.flush();
	}
	
	public void persistStratFeedBbDataBands(float lowerBand, float upperBand, float middleBand, int liveId) {
		Stratfeed stratfeed = new Stratfeed();
		
		stratfeed.setMiddleBand(middleBand);
		stratfeed.setUpperBand(upperBand);
		stratfeed.setLowerBand(lowerBand);
		stratfeed.setStrategy("BB");
		stratfeed.setLivemarketdata(liveDataBean.getLivemarketdataById(liveId));
		
		entityManager.persist(stratfeed);
		entityManager.flush();
	}
	
	//******************** PERSISTENCE METHODS FOR TWO MOVING AVG STRATEGY************************************
	
	public void persistStratFeedDataSma(float shortMa, float price, int liveId) {
		Stratfeed stratfeed = new Stratfeed();
		
		stratfeed.setShortMa(shortMa);
		stratfeed.setStrategy("TMA");
		//stratfeed.setPrice(price);
		stratfeed.setLivemarketdata(liveDataBean.getLivemarketdataById(liveId));
		
		entityManager.persist(stratfeed);
		entityManager.flush();
	}
	
	public void persistStratFeedDataNullSma(float price, int liveId) {
		Stratfeed stratfeed = new Stratfeed();
		
		//stratfeed.setPrice(price);
		stratfeed.setLivemarketdata(liveDataBean.getLivemarketdataById(liveId));
		stratfeed.setStrategy("TMA");
		entityManager.persist(stratfeed);
		entityManager.flush();
	}
	
	public void persistStratFeedDataLma(float longMa, float shortMa, float price, int liveId) {
		Stratfeed stratfeed = new Stratfeed();
		
		stratfeed.setLongMa(longMa);
		stratfeed.setShortMa(shortMa);
		stratfeed.setStrategy("TMA");
		//stratfeed.setPrice(price);
		stratfeed.setLivemarketdata(liveDataBean.getLivemarketdataById(liveId));
		
		entityManager.persist(stratfeed);
		entityManager.flush();
	}
	
	public void persistStratFeedDataNullLma(float shortMa, float price, int liveId) {
		Stratfeed stratfeed = new Stratfeed();
		
		stratfeed.setShortMa(shortMa);
		stratfeed.setStrategy("TMA");
		//stratfeed.setPrice(price);
		stratfeed.setLivemarketdata(liveDataBean.getLivemarketdataById(liveId));
		
		entityManager.persist(stratfeed);
		entityManager.flush();
	}
	
	public void persistStratFeedData(String stratFeedString) {
		Stratfeed stratfeed = new Stratfeed();
		
		String[] stratFeedData = stratFeedString.split(",");
		stratfeed.setLivemarketdata(liveDataBean.getLivemarketdataById(Integer.parseInt(stratFeedData[0])));
		stratfeed.setStrategy(stratFeedData[1]);
		stratfeed.setShortMa(Float.parseFloat(stratFeedData[2]));
		stratfeed.setLongMa(Float.parseFloat(stratFeedData[3]));
		stratfeed.setUpperBand(Float.parseFloat(stratFeedData[4]));
		stratfeed.setLowerBand(Float.parseFloat(stratFeedData[5]));
		stratfeed.setMiddleBand(Float.parseFloat(stratFeedData[6]));
		
		entityManager.persist(stratfeed);
		entityManager.flush();
	}
	
	
}
