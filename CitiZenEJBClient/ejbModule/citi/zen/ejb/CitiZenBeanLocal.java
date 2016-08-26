package citi.zen.ejb;

import java.util.List;

import javax.ejb.Local;

import citi.zen.jpa.StockHistory;

@Local
public interface CitiZenBeanLocal {

	public void addStockHistory();
	public List<StockHistory> getAllStocks();
}
