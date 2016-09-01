package com.citi.citizen_app.data.trader.EJB;

import javax.ejb.Stateless;

@Stateless
public class RiskManagerBean {

	public RiskManagerBean(float threshold) {
		// TODO Auto-generated constructor stub
	}

	public boolean exceedLossThreshold(Object currentPrice, Object buyPrice) {
		// TODO Auto-generated method stub
		return false;
	}
	public RiskManagerBean() {
		
	}

}
