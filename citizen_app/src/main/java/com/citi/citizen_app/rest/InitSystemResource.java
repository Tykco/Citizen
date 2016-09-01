package com.citi.citizen_app.rest;

import java.util.Timer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.citi.citizen_app.data.services.YahooTimerServiceBean;
import com.citi.citizen_app.data.trader.EJB.TradeReceiveBean;
import com.citi.citizen_app.data.trader.EJB.TradeSendBean;

@Path("/init")
@SuppressWarnings("unchecked")
@RequestScoped
public class InitSystemResource {

	@Inject
	private YahooTimerServiceBean timerServiceBean;
	@Inject
	private TradeReceiveBean tradeReceiveBean;
	
	/**
	 * METHOD to start the live market data feed for list of tickers.
	 * @param tickers
	 * @return
	 */
	@GET
	@Path("/{tickers}/start")
	public String startTimedTaskYahoo(@PathParam("tickers") String tickers) {
		if (timerServiceBean==null) {
			System.out.println("bean is null");
			return null;
		} else {
	        //running timer task as daemon thread
			Timer timer = new Timer(true);
			timerServiceBean.setTickerList(tickers);
			timer.scheduleAtFixedRate(timerServiceBean, 0, 1040);
	        
			printToBrowser(tickers);
			//Inits OrderBroker Sender and Receiver.
			System.out.println("Initializing OrderBroker Receiver.....");
			initOrderBrokerReceiver();
			System.out.println("POST INIT ORDERBROKER............");
			return printToBrowser(tickers);
		}
	}
	
	private String printToBrowser(String tickers) {
		System.out.println("TimerTask started...");
        String[] items = tickers.split(",");
        String browserOutput = "";
        for (int i = 0; i < items.length; i++) {
        	browserOutput += "<p>PERSISTING LIVEMARKET DATA TABLE FOR "+ items[i] +"!</p>";
        }
        return browserOutput;
	}
	
	private void initOrderBrokerReceiver() {
		try {
			System.out.println("RUNNING RUNRECEIVER METHOD");
			tradeReceiveBean.runReceiver();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
