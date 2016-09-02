package com.citi.citizen_app.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.citi.citizen_app.data.repository.EJB.RepositoryPortfolioDataBean;
import com.citi.citizen_app.model.Portfolio;

@Path("/portfolio")
@RequestScoped
@SuppressWarnings("unchecked")
//http://localhost:8080/citizen_app/rest/portfolio/{username} 
public class PortfolioDataResource {

	@Inject
	private RepositoryPortfolioDataBean portfolioBean;
	
	@GET
	@Produces("application/json")
	@Path("/{username}")
	public List<Portfolio> getPortfolioByUsername(@PathParam("username") String username) {
		if (portfolioBean==null) {
			return null;
		} else {
			return portfolioBean.getPortfoliosByUsername(username);
		}
	}
}
