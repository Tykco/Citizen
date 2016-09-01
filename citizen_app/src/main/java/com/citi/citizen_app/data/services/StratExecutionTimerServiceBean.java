package com.citi.citizen_app.data.services;

import java.util.HashMap;
import java.util.TimerTask;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.citi.citizen_app.data.strategy.BollingerBandStrategyBean;
import com.citi.citizen_app.data.strategy.TwoMovingAvgStrategyBean;
import com.citi.citizen_app.data.trader.EJB.RiskManagerBean;

@Stateless
public class StratExecutionTimerServiceBean extends TimerTask {

	@Inject
	private TwoMovingAvgStrategyBean tmaStratBean;
	@Inject
	private BollingerBandStrategyBean bbStratBean;
	
	private HashMap<String,TwoMovingAvgStrategyBean> movingAvgHashMap = new HashMap<String,TwoMovingAvgStrategyBean>();
	private HashMap<String,BollingerBandStrategyBean> bollingerHashMap = new HashMap<String,BollingerBandStrategyBean>();
	private HashMap<String,RiskManagerBean> riskHashMap = new HashMap<String,RiskManagerBean>();

	private String ticker;
	private Boolean bollinger = false;

	@Override
	public void run() {
		//To call the 2MA strategy on IBM, the below statements need to be call repeatedly
		//TODO: Place in TimerTask
		if (bollinger){
			bbStratBean.executeStrategy();
			bbRunRisk();
		} else {
			tmaStratBean.executeStrategy();
			tmaRunRisk();
		}
	}
	
	private void tmaRunRisk() {
		if(movingAvgHashMap.get(ticker).hasStocks()){
			boolean sellStock = riskHashMap.get(ticker).exceedLossThreshold(movingAvgHashMap
					.get(ticker).getCurrentPrice(), 
					movingAvgHashMap.get(ticker).getBuyPrice());
			if(sellStock){
				//TODO: EXECUTE SELL COMMAND HERE
				System.out.println("TMA RISK SELL!!!!!!");
				movingAvgHashMap.get(ticker).setHasStocks(false);
			}
		}
	}
	
	private void bbRunRisk() {
		if(bollingerHashMap.get(ticker).hasStocks()){
			boolean sellStock = riskHashMap.get(ticker).exceedLossThreshold(bollingerHashMap
					.get(ticker).getCurrentPrice(), 
					bollingerHashMap.get(ticker).getBuyPrice());
			if(sellStock){
				//TODO: EXECUTE SELL COMMAND HERE
				System.out.println("BB RISK SELL!!!!!!");
				bollingerHashMap.get(ticker).setHasStocks(false);
			}
		}
	}

	public void startTmaStrategy(float shortMa, float longMa, 
			int quantity, float threshold,
			String strategy, String ticker) {
		this.ticker = ticker;
		
		
		tmaStratBean.setParams(ticker, shortMa, longMa, quantity);
		movingAvgHashMap.put(ticker, tmaStratBean);
		riskHashMap.put(ticker, new RiskManagerBean(threshold));
	}

	public void startBbStrategy(float ma, int quantity, 
			float threshold, float stdDiv, String tick) {
		bollinger= true;
		ticker=tick;

		bbStratBean.setParams(ma, quantity, threshold, stdDiv, ticker);
		bollingerHashMap.put(ticker, bbStratBean);
		riskHashMap.put(ticker, new RiskManagerBean(threshold));
	}

}
