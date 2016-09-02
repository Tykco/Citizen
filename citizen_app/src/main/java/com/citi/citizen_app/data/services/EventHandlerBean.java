package com.citi.citizen_app.data.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.citi.citizen_app.data.trader.EJB.TradeReceiveBean;

@Stateless
public class EventHandlerBean {

	@Inject
	private TradeReceiveBean tradeReceiveBean;
	
	public void initOrderBrokerReceiver() {
		try {
			System.out.println("RUNNING RUNRECEIVER METHOD");
			tradeReceiveBean.runReceiver();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
