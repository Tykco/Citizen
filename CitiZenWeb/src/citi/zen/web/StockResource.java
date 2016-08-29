package citi.zen.web;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import citi.zen.ejb.CitiZenBeanLocal;
import citi.zen.jpa.Stock;

@Path("/stock")
@SuppressWarnings("unchecked")
public class StockResource {
	
	private CitiZenBeanLocal bean;
	
	private String jndiName = "java:app/CitiZenEJB/"
			+ "CitiZenBean!citi.zen.ejb.CitiZenBeanLocal";

	
	public StockResource() {
		try {
			InitialContext context = new InitialContext();
			bean = (CitiZenBeanLocal) context.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Produces("application/json")
	public List<Stock> getAllStocks() {
		if (bean==null) {
			return null;
		} else {
			return bean.getAllStocks();
		}
	}
	
	
}