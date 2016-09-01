package com.citi.citizen_app.data.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.citi.citizen_app.data.repository.EJB.RepositoryLiveDataBean;

@Stateless
public class MockYahooServiceBean {

	@Inject
	private RepositoryLiveDataBean liveDataBean;
	/**
    Want to demo a ticker that isn't shown below? Just add another line.
    Or expect it to trade at prices hovering around 100.
	 */
	private double getOpenPrice (String ticker)
	{
		if (ticker.equalsIgnoreCase ("AAPL")) return 107;
		if (ticker.equalsIgnoreCase ("GOOG")) return 769.54;
		if (ticker.equalsIgnoreCase ("BRK-A")) return 100000;
		if (ticker.equalsIgnoreCase ("NSC")) return 194000;
		if (ticker.equalsIgnoreCase ("MSFT")) return 43;
		if (ticker.equalsIgnoreCase ("IBM")) return 159.40;

		//TODO hash ticker and come up with a starting price
		return 100;
	}

	public double getMarketPrice (String ticker, long seed)
	{
		final long PERIOD_LONG = 2 * 60 * 1000; // two minutes
		final long PERIOD_SHORT = PERIOD_LONG * 3 / 20;

		double open = getOpenPrice (ticker);
		double magnitudeLong = open * 0.05;
		double magnitudeShort = magnitudeLong * 0.10;

		double varianceLong = 
				Math.sin (Math.PI * 2 * (seed % PERIOD_LONG) / PERIOD_LONG) * magnitudeLong;
		double varianceShort = 
				Math.sin (Math.PI * 2 * (seed % PERIOD_SHORT) / PERIOD_SHORT) * magnitudeShort;

		return open + varianceLong + varianceShort;
	}

	public double getMarketPriceNow (String ticker)
	{
		return getMarketPrice (ticker, System.currentTimeMillis ());
	}

	
	public void generateMarketData(String tickers, String format) {

		if (tickers.length () == 0 || format.length () == 0 || format.length () % 2 == 1)
			throw new WebApplicationException 
			(Response.status (Status.BAD_REQUEST)
					.entity ("tickers and format must each have values.")
					.build ());

		String[] stocks = tickers.split(",");
		String[] fields = new String[format.length () / 2];
		for (int i = 0; i < format.length () / 2; ++i)
			fields[i] = format.substring (i * 2, i * 2 + 2);

		StringBuilder result = new StringBuilder ();
		for (String stock : stocks) {
			
			for (String field : fields) {
			
				if (field.equals ("s0")) 
					result.append (stock);
				else if (field.equals("o0"))
					result.append(getOpenPrice(stock));
				else if (field.equals ("p0") || field.equals ("l1") ||
						field.equals ("a0") || field.equals ("b0")) 
					result.append 
					(String.format ("%1.2f", getMarketPriceNow (stock)));
				else if (field.equals ("v0"))
					result.append ("1000");
				else
					result.append ("N/A");

				result.append (",");
			}
			result.deleteCharAt (result.length () - 1);
			
			liveDataBean.persistLiveData(result.toString());
			//System.out.println("AtMock: "+ result.toString());
			result.setLength(0);
		}
		
	}


}
