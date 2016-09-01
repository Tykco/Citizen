package com.citi.citizen_app.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.citi.citizen_app.data.repository.EJB.RepositoryStratFeedDataBean;
import com.citi.citizen_app.model.Stratfeed;

@Path("/stratfeed")
@SuppressWarnings("unchecked")
public class StratFeedResource {
	
	@Inject
	private RepositoryStratFeedDataBean stratFeedBean;

	//e.g. /.../goog/tma?liveid=100
	
	/**
	 * Returns all stratFeedData after liveId.
	 * e.g. usage: /.../goog/tma?liveid=100
	 * @param ticker
	 * @param strategy
	 * @param liveId
	 * @return
	 */
	@GET
	@Produces("application/json")
	@Path("/{ticker}/{strategy}")
	public List<Stratfeed> getStratFeedByTickerStrategyLiveId(
									@PathParam("ticker") String ticker,
									@PathParam("strategy") String strategy,
									@QueryParam("liveid") int liveId) {
		if (stratFeedBean==null) {
			return null;
		} else {
			return stratFeedBean.getStratFeedByTickerStrategyLiveId(ticker, strategy, liveId);
		}
	}
}
