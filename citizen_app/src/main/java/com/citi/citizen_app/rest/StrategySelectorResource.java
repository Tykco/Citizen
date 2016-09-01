package com.citi.citizen_app.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.citi.citizen_app.data.trader.EJB.StrategySelectorManagerBean;
import com.citi.citizen_app.data.trader.EJB.TradeReceiveBean;
import com.citi.citizen_app.data.trader.EJB.TradeSendBean;

@Path("/strat")
@SuppressWarnings("unchecked")
public class StrategySelectorResource {

	@Inject
	private StrategySelectorManagerBean bean;
	
	//Pass in multiple tickers separated by ","
	//STARTS STRATEGY
	@GET
	@Produces("application/json")
	@Path("/{ticker}/{strategy}/start")
	public String getLiveDataByTicker(@PathParam("ticker") String ticker,
										@PathParam("strategy") String strategy,
										@QueryParam("longma") float longMa,
										@QueryParam("shortma") float shortMa,
										@QueryParam("quantity") int quantity,
										@QueryParam("threshold") float threshold,
										@QueryParam("stdDiv") float stdDiv,
										@QueryParam("bbma") float bbma) {
		if (bean==null) {
			return null;
		} else {
			System.out.println("Passing user's ONE STOCK params into strat selector bean...");
			bean.handleUserStratParameters(shortMa, longMa, quantity, 
											threshold, strategy, ticker, bbma, stdDiv);
			return "<p>PASSING USER'S PARAMS FOR "+ ticker +" USING "+strategy +"!</p>";
		}
	}
	
}
