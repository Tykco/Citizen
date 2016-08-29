package citi.zen.ejb;

import java.util.List;

import citi.zen.jpa.Livemarketdata;

public interface RepositoryLiveDataBeanRemote {

	public List<Livemarketdata> getLiveDataByTicker(String ticker);
}
