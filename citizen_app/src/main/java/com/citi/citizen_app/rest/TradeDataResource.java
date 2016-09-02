package com.citi.citizen_app.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.citi.citizen_app.data.repository.EJB.RepositoryTradeDataBean;
import com.citi.citizen_app.model.Trade;

@Path("/trade")
@RequestScoped
@SuppressWarnings("unchecked")
//http://localhost:8080/citizen_app/rest/trade?pfid=1 
public class TradeDataResource {

	@Inject
	private RepositoryTradeDataBean tradeBean;
	
	@GET
	@Produces("application/json")
	public List<Trade> getPortfolioByUsernamer(@QueryParam("pfid") int pfid) {
		if (tradeBean==null) {
			return null;
		} else {
			return tradeBean.getTradeByPortfolioId(pfid);
		}
	}
}
