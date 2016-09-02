package com.citi.citizen_app.data.trader.EJB;

import java.util.Properties;

import javax.enterprise.context.Dependent;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.citi.citizen_app.data.trader.orderbroker.OrderConstants;

@Dependent
public class TradeReceiveBean implements MessageListener {
	
	private QueueConnection queueConnection;
	private QueueSession queueSession;
	private Queue myQueue;
	private QueueReceiver queueReceiver;
	private boolean quit = false;

	public void runReceiver() throws Exception {

		InitialContext ic = getInitialContext();
		
		init(ic);
		System.out.println("Receiver is ready to receive messages (To quit, send a \"quit\" message from QueueSender.class).");
		
		// Waiting until a "quit" message has been received on the queue.
		synchronized (this) {
			while (!quit) {
				try {
					wait();
				} 
				catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
		close();
		System.out.println("RECEIVER CLOSED!");
	}

	
	private InitialContext getInitialContext() throws NamingException {

		InitialContext context = null;
		
		try {
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY, OrderConstants.JMS_CONNECTION_FACTORY_JNDI);
			props.put(Context.PROVIDER_URL, OrderConstants.WILDFLY_REMOTING_URL);

			context = new InitialContext(props);
			System.out.println("\n\tGot initial Context: " + context);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return context;
		
	}

	
	private void init(Context ctx) throws NamingException, JMSException {

		QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) ctx.lookup(OrderConstants.JMS_QUEUE_CONNECTION_FACTORY_JNDI);

		queueConnection = queueConnectionFactory.createQueueConnection();
		queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		myQueue = (Queue) ctx.lookup(OrderConstants.JMS_QUEUE_RETURN_JNDI);
		
		queueReceiver = queueSession.createReceiver(myQueue);
		queueReceiver.setMessageListener(this);
		queueConnection.start();
		
	}


	public void onMessage(Message msg) {
		
		try {
			System.out.println("RECEIVING SOME MSG: " + msg);
			
			String msgText;
			
			if (msg instanceof TextMessage) {
				
				msgText = ((TextMessage) msg).getText();
				System.out.println("onMESSAGE MSGTEXT: "+msgText);
				
				msgText = msgText.substring(55);
				
			/*	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			    DocumentBuilder builder = factory.newDocumentBuilder();
			    InputSource is = new InputSource(new StringReader(msgText));
			    builder.parse(is).get*/
				
			} else {
				System.out.println("Not an instance of Text Message");
			}

		} catch (JMSException e) {

			e.printStackTrace();
		}
		
	}


	private void close() throws JMSException {
		queueReceiver.close();
		queueSession.close();
		queueConnection.close();
	}
}
