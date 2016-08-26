package citi.zen.ejb;

import java.util.List;

import javax.ejb.Remote;

import citi.zen.jpa.StockHistory;

@Remote
public interface CitiZenBeanRemote {

	public void addStockHistory();
	public List<StockHistory> getAllStocks();
}
