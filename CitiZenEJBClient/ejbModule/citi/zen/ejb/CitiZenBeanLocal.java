package citi.zen.ejb;

import java.util.List;

import javax.ejb.Local;

import citi.zen.jpa.Stock;

@Local
public interface CitiZenBeanLocal {

	public List<Stock> getAllStocks();
}