package com.citi.citizen_app.data.trader.EJB;

import java.util.Timer;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.citi.citizen_app.data.services.StratExecutionTimerServiceBean;

/**
 * This bean handles the parameters passed in from client side
 * to be submitted into the strategy bean.
 *
 */
@Stateless
public class StrategySelectorManagerBean {

	@Inject
	private RiskManagerBean riskBean;
	@Inject
	private StratExecutionTimerServiceBean stratTmaTimerTaskBean;
	@Inject
	private StratExecutionTimerServiceBean stratBbTimerTaskBean;
	@Inject
	private PortfolioPositionManagerBean portfolioPositionManagerBean;
	
	
	private String tmaStrategy = "tma";
	private String bbStrategy = "bb";
	
	//Called BY StratSelectorResource
	public void handleUserStrategyParameters(float shortMa, float longMa, 
											int quantity, float threshold,
											String strategy, String ticker, 
											float bbma, float stdDiv, int portfolioId) {
//		StringBuilder stratString = new StringBuilder();
//		stratString.append(ticker).append(",")
//					.append(longMa).append(",")
//					.append(shortMa).append(",")
//					.append(quantity).append(",")
//					.append(threshold);
		
		//Insert new positions and portfolio for this trader.
		portfolioPositionManagerBean.insertPortfolioPositionFirst(portfolioId, ticker);
		
		if (strategy.equalsIgnoreCase(tmaStrategy) ) {
			
			System.out.println("INITIALIZING TMA STRAT BEAN....");
			
			stratTmaTimerTaskBean.startTmaStrategy(shortMa, longMa, 
												quantity, threshold,
												strategy, ticker, portfolioId);
			
			Timer timer = new Timer(true);
			timer.scheduleAtFixedRate(stratTmaTimerTaskBean, 0, 1040);
			
		} else if (strategy.equalsIgnoreCase(bbStrategy)) {
			System.out.println("INITIALIZING BB STRAT BEAN...");
			
			stratBbTimerTaskBean.startBbStrategy(bbma, quantity, threshold,
													stdDiv, ticker, portfolioId);
			Timer timer = new Timer(true);
			timer.scheduleAtFixedRate(stratBbTimerTaskBean, 0, 1040);
		}
	}
}
