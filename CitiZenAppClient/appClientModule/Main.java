import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import citi.zen.ejb.CitiZenBeanRemote;
import citi.zen.jpa.StockHistory;



public class Main {

	public static void main(String[] args) throws NamingException {
		// Create Properties for JNDI InitialContext.
		Properties prop = new Properties();
		prop.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName()); 
		prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		prop.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		prop.put("jboss.naming.client.ejb.context", true);

		// Create the JNDI InitialContext.
		Context context = new InitialContext(prop);

		// Formulate the full JNDI name for the CitiZen session bean.
		String appName     = "CitiZen";
		String moduleName  = "CitiZenEJB";
		String beanName    = "CitiZenBean";
		String packageName = "citi.zen.ejb";
		String className   = "CitiZenBeanRemote";

		// Lookup the bean using the full JNDI name.
		String fullJndiName = String.format("%s/%s/%s!%s.%s", appName, moduleName, beanName, packageName, className);
		CitiZenBeanRemote bean = (CitiZenBeanRemote) context.lookup(fullJndiName);

		bean.addStockHistory();
		
		List<StockHistory> stockHistory = bean.getAllStocks();
		displayProducts("All stocks", stockHistory);
		
		/*bean.addCategoriesAndProducts();
		List<Product> products = bean.getAllProducts();
		displayProducts("All products", products);

		products = bean.getProductsByName("jersey");
		displayProducts("Products by name", products);

		products = bean.getProductsInCategory("Sports");
		displayProducts("Products in category", products);

		bean.increaseAllPrices((double) 10);
		products = bean.getAllProducts();
		displayProducts("All products after 10.00 price increase", products);*/

	}


	private static void displayProducts(String message, List<StockHistory> stockHistory) {
		System.out.printf("\n%s\n", message);
		for (StockHistory stockHistoryObject: stockHistory) {
			System.out.println(stockHistoryObject);
		}
	}
}
