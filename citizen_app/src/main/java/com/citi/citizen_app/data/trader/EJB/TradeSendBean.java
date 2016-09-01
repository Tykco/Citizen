package com.citi.citizen_app.data.trader.EJB;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

import javax.enterprise.context.Dependent;
//jms stuff
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.citi.citizen_app.data.trader.orderbroker.OrderConstants;

/**
 * This Bean is invoked at the instance when trading
 * strategy executes a buy or sell.
 */
@Dependent
public class TradeSendBean {

	private QueueConnection queueConnection;
	private QueueSession queueSession;
	private Queue myQueue;
	private QueueSender queueSender;


	public void runSender(String buyE, String priceE, String sizeE, String stockE) throws Exception {
		InitialContext ic = getInitialContext();
		init(ic);
		sendToOrderBrokerLean(buyE, priceE, sizeE, stockE);
		close();
		System.out.println("runSender CLOSED!");
	}


	private InitialContext getInitialContext() throws NamingException {

		InitialContext context = null;

		try {
			Properties props = new Properties();
			props.setProperty (Context.INITIAL_CONTEXT_FACTORY, OrderConstants.JMS_CONNECTION_FACTORY_JNDI);
			props.setProperty (Context.PROVIDER_URL, OrderConstants.WILDFLY_REMOTING_URL);

			context = new InitialContext(props);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return context;

	}


	private void init(Context ctx) throws NamingException, JMSException {

		QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) ctx.lookup(OrderConstants.JMS_QUEUE_CONNECTION_FACTORY_JNDI);
		queueConnection = queueConnectionFactory.createQueueConnection();
		queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

		myQueue = (Queue) ctx.lookup(OrderConstants.JMS_QUEUE_JNDI);
		queueSender = queueSession.createSender(myQueue);
		queueConnection.start();

	}


	public void sendToOrderBroker(String buyE, String idE, String priceE, String sizeE, String stockE, String whenAsDateE) throws IOException, JMSException {


		/*DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("trade");
			doc.appendChild(rootElement);

			// buy elements
			Element buy = doc.createElement("buy");
			buy.appendChild(doc.createTextNode("true"));
			rootElement.appendChild(buy);

			// id elements
			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode("0"));
			rootElement.appendChild(id);

			// price elements
			Element price = doc.createElement("price");
			price.appendChild(doc.createTextNode("88.0"));
			rootElement.appendChild(price);

			// size elements
			Element size = doc.createElement("size");
			size.appendChild(doc.createTextNode("2000"));
			rootElement.appendChild(size);

			// stock elements
			Element stock = doc.createElement("stock");
			stock.appendChild(doc.createTextNode("HON"));
			rootElement.appendChild(stock);

			// whenAsDate elements
			Element whenAsDate = doc.createElement("whenAsDate");
			whenAsDate.appendChild(doc.createTextNode("2014-07-31T22:33:22.801-04:00"));
			rootElement.appendChild(whenAsDate);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\Users\\Lenovo\\Desktop\\Citi Project\\file.xml"));
			//StreamResult result = new StreamResult(new File("C:\Users\Lenovo\Desktop\Citi Project"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");*/

		String testMsg = "";
		testMsg += "<trade>";
		testMsg += "<buy>" + buyE + "</buy>";
		testMsg += "<id>" + idE + "</id>";
		testMsg += "<price>" + priceE + "</price>";
		testMsg += "<size>" + sizeE + "</size>";
		testMsg += "<stock>" + stockE + "</stock>";
		testMsg += "<whenAsDate>" + whenAsDateE + "</whenAsDate>";
		testMsg += "</trade>";

		System.out.println(testMsg);

		TextMessage msg = queueSession.createTextMessage(testMsg);
		msg.setIntProperty("counter", 1);
		msg.setJMSCorrelationID("1");
		queueSender.send(msg);


	}

	public void sendToOrderBrokerLean(String buyE, String priceE, String sizeE, String stockE) throws IOException, JMSException {


		/*DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("trade");
			doc.appendChild(rootElement);

			// buy elements
			Element buy = doc.createElement("buy");
			buy.appendChild(doc.createTextNode("true"));
			rootElement.appendChild(buy);

			// id elements
			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode("0"));
			rootElement.appendChild(id);

			// price elements
			Element price = doc.createElement("price");
			price.appendChild(doc.createTextNode("88.0"));
			rootElement.appendChild(price);

			// size elements
			Element size = doc.createElement("size");
			size.appendChild(doc.createTextNode("2000"));
			rootElement.appendChild(size);

			// stock elements
			Element stock = doc.createElement("stock");
			stock.appendChild(doc.createTextNode("HON"));
			rootElement.appendChild(stock);

			// whenAsDate elements
			Element whenAsDate = doc.createElement("whenAsDate");
			whenAsDate.appendChild(doc.createTextNode("2014-07-31T22:33:22.801-04:00"));
			rootElement.appendChild(whenAsDate);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:\\Users\\Lenovo\\Desktop\\Citi Project\\file.xml"));
			//StreamResult result = new StreamResult(new File("C:\Users\Lenovo\Desktop\Citi Project"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");*/


		Locale locale1 = Locale.US;
		TimeZone tz1 = TimeZone.getTimeZone("EST");
		String whenAsDateE = Calendar.getInstance(tz1, locale1).getTime().toString();
		
		String testMsg = "";
		testMsg += "<trade>";
		testMsg += "<buy>" + buyE + "</buy>";
		testMsg += "<price>" + priceE + "</price>";
		testMsg += "<size>" + sizeE + "</size>";
		testMsg += "<stock>" + stockE + "</stock>";
		testMsg += "<whenAsDate>" + whenAsDateE + "</whenAsDate>";
		testMsg += "</trade>";

		System.out.println(testMsg);

		TextMessage msg = queueSession.createTextMessage(testMsg);
		msg.setIntProperty("counter", 1);
		msg.setJMSCorrelationID("1");
		queueSender.send(msg);


	}


	private void close() throws JMSException {
		queueSender.close();
		queueSession.close();
		queueConnection.close();
	}

}
