package com.citi.citizen_app.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.citi.citizen_app.data.repository.EJB.RepositoryPositionDataBean;
import com.citi.citizen_app.model.Position;

@Path("/position")
@RequestScoped
@SuppressWarnings("unchecked")
//http://localhost:8080/citizen_app/rest/position?pfid=1 
public class PositionDataResource {

	@Inject
	private RepositoryPositionDataBean positionBean;
	
	@GET
	@Produces("application/json")
	//E.g. http://localhost:8080/citizen_app/rest/livedata/goog
	public List<Position> getPositionsByPortfolioId(@QueryParam("pfid") int pfid) {
		if (positionBean==null) {
			return null;
		} else {
			return positionBean.getPositionsByPortfolioId(pfid);
		}
	}
}
