package com.citi.citizen_app.rest;

import java.math.BigDecimal;
import java.util.Timer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.citi.citizen_app.data.repository.EJB.RepositoryPortfolioDataBean;
import com.citi.citizen_app.data.services.EventHandlerBean;
import com.citi.citizen_app.data.services.YahooTimerServiceBean;

@Path("/init")
@SuppressWarnings("unchecked")
@RequestScoped
public class InitSystemResource {

	@Inject
	private YahooTimerServiceBean timerServiceBean;
	@Inject
	private EventHandlerBean eventBean;
	
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
			eventBean.initOrderBrokerReceiver();
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
	
	
}
