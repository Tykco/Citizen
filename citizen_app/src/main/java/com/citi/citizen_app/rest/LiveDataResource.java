package com.citi.citizen_app.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.citi.citizen_app.data.repository.EJB.RepositoryLiveDataBean;
import com.citi.citizen_app.model.Livemarketdata;

@Path("/livedata")
@RequestScoped
@SuppressWarnings("unchecked")
public class LiveDataResource {

	@Inject
	private RepositoryLiveDataBean bean;

	
	@GET
	@Produces("application/json")
	@Path("/{ticker}")
	//E.g. http://localhost:8080/citizen_app/rest/livedata/goog
	public List<Livemarketdata> getLiveDataByTicker(@PathParam("ticker") String ticker) {
		if (bean==null) {
			return null;
		} else {
			return bean.getLiveDataByTicker(ticker);
		}
	}
	
	/**
	 * Returns livemarketdata for a stock where id > tableid
	 * @param ticker
	 * @param tableId
	 * @return
	 */
	@GET
	@Produces("application/json")
	@Path("/{ticker}")
	//E.g. http://localhost:8080/citizen_app/rest/livedata/goog?livedata=20
	public List<Livemarketdata> getLiveDataByTickerIdConstraint(@PathParam("ticker") String ticker, 
																@QueryParam("tableId") int tableId) {
		if (bean==null) {
			return null;
		} else {
			return bean.getLiveDataByTickerIdConstraint(ticker, tableId);
		}
	}
}
