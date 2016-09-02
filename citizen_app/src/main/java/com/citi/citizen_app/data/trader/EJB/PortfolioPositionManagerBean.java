package com.citi.citizen_app.data.trader.EJB;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.citi.citizen_app.data.repository.EJB.RepositoryPortfolioDataBean;
import com.citi.citizen_app.data.repository.EJB.RepositoryPositionDataBean;

@Stateless
public class PortfolioPositionManagerBean {
	@Inject
	private RepositoryPositionDataBean positionBean;
	@Inject
	private RepositoryPortfolioDataBean portfolioBean;
	
	
	public void insertPortfolioPositionFirst(int portfolioId, String ticker) {
		int userId = 1;
		portfolioBean.persistPortfolio(userId);
		positionBean.persistPosition(portfolioId, ticker);
	}
	
}
