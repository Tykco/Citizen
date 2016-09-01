package com.citi.citizen_app.data.trader.orderbroker;

public class OrderConstants {

	//public final static String JMS_CONNECTION_FACTORY_JNDI = "jms/RemoteConnectionFactory";
	//public final static String JMS_QUEUE_JNDI = "jms/queue/TestQ";
	//public final static String JMS_USERNAME = "limjunyangleon";     // The role for this user is "guest" in ApplicationRealm
	//public final static String JMS_PASSWORD = "dammadko";
	//public final static String WILDFLY_REMOTING_URL = "http-remoting://localhost:8080";

	public final static String JMS_CONNECTION_FACTORY_JNDI = "org.apache.activemq.jndi.ActiveMQInitialContextFactory";
	public final static String JMS_QUEUE_JNDI = "dynamicQueues/OrderBroker";
	public final static String JMS_QUEUE_RETURN_JNDI = "dynamicQueues/OrderBroker_Reply";
	public final static String WILDFLY_REMOTING_URL = "tcp://localhost:61616";
	//public final static String WILDFLY_REMOTING_URL = "tcp://192.168.1.104:61616";
	public final static String JMS_QUEUE_CONNECTION_FACTORY_JNDI = "ConnectionFactory";
}
