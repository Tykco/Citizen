package citi.zen.web;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import citi.zen.ejb.RepositoryLiveDataBeanLocal;
import citi.zen.jpa.Livemarketdata;
import citi.zen.jpa.Stock;

@Path("/livedata")
@SuppressWarnings("unchecked")
public class LiveDataResource {

	private RepositoryLiveDataBeanLocal bean;

	private String beanName = "RepositoryLiveDataBean";
	private String beanInterfaceName = "RepositoryLiveDataBeanLocal";

	private String jndiName = "java:app/CitiZenEJB/"
								+ beanName
								+ "!citi.zen.ejb."
								+beanInterfaceName;


	public LiveDataResource() {
		try {
			InitialContext context = new InitialContext();
			bean = (RepositoryLiveDataBeanLocal) context.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Produces("application/json")
	@Path("/{ticker}")
	public List<Livemarketdata> getLiveDataByTicker(@PathParam("ticker") String ticker) {
		if (bean==null) {
			return null;
		} else {
			return bean.getLiveDataByTicker(ticker);
		}
	}
}
