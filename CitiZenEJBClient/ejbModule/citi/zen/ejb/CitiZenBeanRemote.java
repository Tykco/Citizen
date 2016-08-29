package citi.zen.ejb;

import java.util.List;

import javax.ejb.Remote;

import citi.zen.jpa.Stock;

@Remote
public interface CitiZenBeanRemote {

	public List<Stock> getAllStocks();
}