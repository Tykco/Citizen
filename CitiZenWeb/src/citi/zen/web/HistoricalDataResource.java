package citi.zen.web;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import citi.zen.ejb.RepositoryHistDataBeanLocal;
import citi.zen.jpa.Historicalmarketdata;

@Path("/histdata")
@SuppressWarnings("unchecked")
public class HistoricalDataResource {
	
	private RepositoryHistDataBeanLocal bean;
	
	private String beanName = "RepositoryHistDataBean";
	private String beanInterfaceName = "RepositoryHistDataBeanLocal";

	private String jndiName = "java:app/CitiZenEJB/"
			+ beanName
			+ "!citi.zen.ejb."
			+beanInterfaceName;
	
	
	public HistoricalDataResource() {
		try {
			InitialContext context = new InitialContext();
			bean = (RepositoryHistDataBeanLocal) context.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
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
