package com.citi.citizen_app.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.citi.citizen_app.data.repository.EJB.RepositoryHistDataBean;
import com.citi.citizen_app.model.Historicalmarketdata;

@Path("/histdata")
@RequestScoped
@SuppressWarnings("unchecked")
public class HistDataResource {

	@Inject
	private RepositoryHistDataBean bean;
	
	@GET
	@Produces("application/json")
	@Path("/day/{ticker}")
	public List<Historicalmarketdata> getHistDataDayByTicker(@PathParam("ticker") String ticker) {
		if (bean==null) {
			return null;
		} else {
			return bean.getHistDataDayByTicker(ticker);
		}
	}
	
	@GET
	@Produces("application/json")
	@Path("/monthyear/{ticker}")
	public List<Historicalmarketdata> getHistDataMonthYearByTicker(@PathParam("ticker") String ticker) {
		if (bean==null) {
			return null;
		} else {
			return bean.getHistDataMonthYearByTicker(ticker);
		}
	}
}
