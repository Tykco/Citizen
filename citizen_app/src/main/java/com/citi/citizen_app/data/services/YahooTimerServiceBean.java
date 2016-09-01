package com.citi.citizen_app.data.services;

import java.util.TimerTask;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class YahooTimerServiceBean extends TimerTask {

	@Inject
	private MockYahooServiceBean yahooBean;
	private String tickers = "";
	private String format = "s0o0l1a0b0";

	@Override
	public void run() {
		yahooBean.generateMarketData(tickers, format);
		//System.out.println("t: " + tickers + " f: " + format);
	}
	
	public void setTickerList(String tickers) {
		this.tickers = tickers;
	}
	
}
