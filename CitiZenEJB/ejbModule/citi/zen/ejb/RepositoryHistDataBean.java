package citi.zen.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import citi.zen.jpa.Historicalmarketdata;

@Stateless
@Remote(RepositoryHistDataBeanRemote.class)
@Local(RepositoryHistDataBeanLocal.class)
public class RepositoryHistDataBean implements RepositoryHistDataBeanLocal, RepositoryHistDataBeanRemote {

	@PersistenceContext(unitName="CitiZenJPA")
	EntityManager entityManager;

	@Override
	public List<Historicalmarketdata> getHistDataDayByTicker(String ticker) {
		TypedQuery<Historicalmarketdata> query = entityManager.createQuery(
    			"SELECT h FROM Historicalmarketdata AS h WHERE h.stock.ticker LIKE '%" + ticker + "%' AND h.epochTimestamp IS NOT NULL", Historicalmarketdata.class);
		List<Historicalmarketdata> histDataList = query.getResultList();
    	return histDataList;
	}

	@Override
	public List<Historicalmarketdata> getHistDataMonthYearByTicker(String ticker) {
		TypedQuery<Historicalmarketdata> query = entityManager.createQuery(
    			"SELECT h FROM Historicalmarketdata AS h WHERE h.stock.ticker LIKE '%" + ticker + "%' AND h.date IS NOT NULL", Historicalmarketdata.class);
		List<Historicalmarketdata> histDataList = query.getResultList();
    	return histDataList;
	}

}
