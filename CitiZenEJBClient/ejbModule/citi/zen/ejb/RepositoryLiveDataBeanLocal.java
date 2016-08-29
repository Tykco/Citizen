package citi.zen.ejb;

import java.util.List;

import citi.zen.jpa.Livemarketdata;

public interface RepositoryLiveDataBeanLocal {

	public List<Livemarketdata> getLiveDataByTicker(String ticker);
}
