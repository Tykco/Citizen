package citi.zen.ejb;

import java.util.List;

import citi.zen.jpa.Historicalmarketdata;

public interface RepositoryHistDataBeanLocal {

	public List<Historicalmarketdata> getHistDataDayByTicker(String ticker);
	public List<Historicalmarketdata> getHistDataMonthYearByTicker(String ticker);

}
