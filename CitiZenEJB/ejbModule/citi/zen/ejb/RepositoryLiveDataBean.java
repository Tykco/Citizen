package citi.zen.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import citi.zen.jpa.Livemarketdata;

@Stateless
@Remote(RepositoryLiveDataBeanRemote.class)
@Local(RepositoryLiveDataBeanLocal.class)
public class RepositoryLiveDataBean implements RepositoryLiveDataBeanLocal, RepositoryLiveDataBeanRemote {

	@PersistenceContext(unitName="CitiZenJPA")
	EntityManager entityManager;
	
	
	@Override
	public List<Livemarketdata> getLiveDataByTicker(String ticker) {
		
		TypedQuery<Livemarketdata> query = entityManager.createQuery(
    			"SELECT l FROM Livemarketdata AS l WHERE l.stock.ticker LIKE '%" + ticker + "%'", Livemarketdata.class);
		List<Livemarketdata> liveDataList = query.getResultList();
    	return liveDataList;
	}
	
}
