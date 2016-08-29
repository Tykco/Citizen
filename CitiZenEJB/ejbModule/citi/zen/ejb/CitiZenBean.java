package citi.zen.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import citi.zen.jpa.Stock;

@Stateless
@Remote(CitiZenBeanRemote.class)
@Local(CitiZenBeanLocal.class)
public class CitiZenBean implements CitiZenBeanLocal, CitiZenBeanRemote{

	@PersistenceContext(name="CitiZenJPA")
	private EntityManager entityManager;
	
	/*@Override
	public void addStockHistory() {
		
		StockHistory stockHistory = new StockHistory("AAPL", 40.20, 40.10);
		entityManager.persist(stockHistory);
	}*/
	
	@Override
	public List<Stock> getAllStocks() {

		TypedQuery<Stock> query = entityManager.createQuery("SELECT s FROM Stock AS s", Stock.class);
    	List<Stock> stockList = query.getResultList();
    	return stockList;
	}

	
}