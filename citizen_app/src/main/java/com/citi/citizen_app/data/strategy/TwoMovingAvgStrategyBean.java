package com.citi.citizen_app.data.strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.citi.citizen_app.data.repository.EJB.RepositoryLiveDataBean;
import com.citi.citizen_app.data.repository.EJB.RepositoryStratFeedDataBean;
import com.citi.citizen_app.data.trader.EJB.TradeManagerBean;
import com.citi.citizen_app.data.trader.EJB.TradeSendBean;
import com.citi.citizen_app.model.Livemarketdata;

@Stateless
public class TwoMovingAvgStrategyBean {

	@Inject
	private RepositoryLiveDataBean liveDataBean;
	@Inject
	private RepositoryStratFeedDataBean repoStratFeedBean;
	@Inject
	private TradeSendBean tradeSendBean;
	@Inject
	private TradeManagerBean tradeManagerBean;

	private int dataTimePeriod;
	private float shortMA;
	private float longMA;
	private int quantityBoughtOrSold;
	private int lastShortEMAExecutedID;
	private int lastLongEMAExecutedID;
	private int lastShortPlotID, lastLongPlotID, lastRecordedID, portfolioId;

	private float buyPrice, currentPrice;

	private boolean start,pause,stop;

	private String stockName;

	private final String buy = "BUY";
	private final String sell = "SELL";
	private final String neutral = "NEUTRAL";
	private final String strategy = "TMA";

	private boolean shortMAisHigher,shortSMAexecuted, longSMAexecuted, hasStocks;

	private List<Float> originalPriceList = new ArrayList<Float>();

	private List<Float> shortMAPriceList = new ArrayList<Float>();
	private List<Float> longMAPriceList = new ArrayList<Float>();
	private List<String> tradeDecision = new ArrayList<String>();

	public TwoMovingAvgStrategyBean(String stockName, float shortMA, float longMA, int quantityBoughtOrSold){
		this.stockName = stockName;
		this.dataTimePeriod = 1; // Every 1 second one stock data
		this.shortMA = shortMA;
		this.longMA = longMA;
		this.quantityBoughtOrSold = quantityBoughtOrSold;
		this.lastShortEMAExecutedID = 0;
		this.lastLongEMAExecutedID = 0;
		this.lastShortPlotID = 0;
		this.lastLongPlotID = 0;
		this.shortSMAexecuted = false;
		this.longSMAexecuted = false;
		this.lastRecordedID = 0;	
		this.hasStocks = false;
		this.start = false;
		this.stop = false;
		this.pause = false;
	}
	
	public TwoMovingAvgStrategyBean() {
		
	}


	public void executeStrategy() {

		//Keep querying for data from database

		/*	TO/DO: SELECT ticker, price from Livemarketdata (livemarketdata.liveId will be keyed into StratFeed Table)
		WHERE ticker = "IBM" and liveId > lastRecordedID
		(liveDataBean.getLiveDataByTickerIdConstraint()) 
		 */
		
		System.out.println("stockname: "+ stockName + " lastRID: "+ lastRecordedID);
		List<Livemarketdata> liveDataList = liveDataBean.getLiveDataByTickerIdConstraint(stockName,lastRecordedID);
		System.out.println("size: " + liveDataList.size());
		for (Livemarketdata ld : liveDataList) {
			lastRecordedID = ld.getLiveId();
			System.out.println("print in FOR LOOP: " +lastRecordedID);
			originalPriceList.add(ld.getPrice());

			// TO/DO: Loop through the query resultset, add the key and values into the hashmap

			if(!shortSMAexecuted){

				if(originalPriceList.size() >= (shortMA/dataTimePeriod)){

					//Calculate Simple Moving Average for ShortMA
					Float sma = simpleMovingAverage(shortMA, originalPriceList);
					shortMAPriceList.add(sma);
					shortSMAexecuted = true;
					lastShortEMAExecutedID = (int) (shortMA - 1);

					//TO/DO: Push Simple Moving Average to client specifying that it is for shortMA 
					//URL must have shortMA, longMA and price parameters
					//shortMAPriceList.get(lastShortPlotID), originalPriceList.get(lastshortEMAExecutedID)
					//longMA is empty here

					//TO/DO: Push to stratFeed TABLE the longMA(NULL), shortMA and price, liveId
					System.out.println("PERSIST FIRST shortMA: shortMA: " + String.valueOf(shortMAPriceList.get(lastShortPlotID)));
					repoStratFeedBean.persistStratFeedDataSma(shortMAPriceList.get(lastShortPlotID), 
																ld.getPrice(), lastRecordedID);
					

				}
				else{
					//TO/DO: Push to stratFeed TABLE the longMA(NULL), shortMA(NULL) and price, liveId
					//TO/DO: Push price and liveId to client
					System.out.println("NO MA YET...");
					repoStratFeedBean.persistStratFeedDataNullSma(ld.getPrice(), lastRecordedID);
					
				}
			}

			else if(!longSMAexecuted){
				if(originalPriceList.size() >= (longMA/dataTimePeriod)){
					//Calculate Simple Moving Average for ShortMA
					float sma = simpleMovingAverage(longMA, originalPriceList);
					longMAPriceList.add(sma);
					longSMAexecuted = true;
					lastLongEMAExecutedID = (int) (longMA - 1);

					if(shortMAPriceList.get(0) > longMAPriceList.get(0)){
						shortMAisHigher = true;
					}
				}
				exponentialMovingAverage(true, shortMA, originalPriceList);
				if(longSMAexecuted){
					//TO/DO: Push to stratFeed TABLE the longMA, shortMA and price, liveId
					
					System.out.println("PERSIST LMA: "+ String.valueOf(longMAPriceList.get(lastLongPlotID)) + 
							"shortMA: " + String.valueOf(shortMAPriceList.get(lastShortPlotID)));
					
					repoStratFeedBean.persistStratFeedDataLma(longMAPriceList.get(lastLongPlotID), 
																shortMAPriceList.get(lastShortPlotID), 
																originalPriceList.get(lastLongEMAExecutedID), 
																lastRecordedID);
					
				}
				else{
					//TO/DO: Push to stratFeed TABLE the longMA, shortMA and price, liveId
					//shortMAPriceList.get(lastShortPlotID), originalPriceList.get(lastLongEMAExecutedID)
					//longMA is 0 here
					System.out.println("PERSIST NULL LMA: shortMA: " + String.valueOf(shortMAPriceList.get(lastShortPlotID)));
					
					repoStratFeedBean.persistStratFeedDataNullLma(shortMAPriceList.get(lastShortPlotID), 
																	ld.getPrice(), lastRecordedID);
					
				}
			}
			else if(shortSMAexecuted && longSMAexecuted){
				exponentialMovingAverage(true, shortMA, originalPriceList);
				exponentialMovingAverage(false, longMA, originalPriceList);

				//TO/DO: Push to stratFeed TABLE the longMA, shortMA and price, liveId
				//shortMAPriceList.get(lastShortPlotID), longMAPriceList.get(lastLongPlotID), originalPriceList.get(lastLongEMAExecutedID)
				System.out.println("PERSIST LMA & EVALUATE: "+ "longMA: "+ String.valueOf(longMAPriceList.get(lastLongPlotID)) + 
						"shortMA: " + String.valueOf(shortMAPriceList.get(lastShortPlotID)));
				
				repoStratFeedBean.persistStratFeedDataLma(longMAPriceList.get(lastLongPlotID),
															shortMAPriceList.get(lastShortPlotID),
															originalPriceList.get(lastLongEMAExecutedID),
															lastRecordedID);
				
				//Evaluate decision
				evaluate();

			}
			else if(shortSMAexecuted){
				// Can start with EMA for shortMA first
				exponentialMovingAverage(true, shortMA, originalPriceList);

				//TO/DO: Push to stratFeed TABLE the longMA, shortMA and price, liveId
				//TO/DO: longMA should be empty here
				System.out.println("PERSIST NULL LMA NOT LEGIT: shortMA: " + String.valueOf(shortMAPriceList.get(lastShortPlotID)));
				
				repoStratFeedBean.persistStratFeedDataNullLma(shortMAPriceList.get(lastShortPlotID),
																originalPriceList.get(lastLongEMAExecutedID),
																lastRecordedID);
				
			}
		}
	}

	public void exponentialMovingAverage(boolean shorterMA, float timePeriod, List<Float> originalPriceList){

		int length = (int) (timePeriod / dataTimePeriod);
		float multiplier = (float) (2.0 / (length+1));
		float prevEmaValue, currentEmaValue;
		float closingPrice; 

		if(shorterMA){
			prevEmaValue = shortMAPriceList.get(lastShortPlotID);
			closingPrice = originalPriceList.get(lastShortEMAExecutedID + 1);
			currentPrice = closingPrice;
			currentEmaValue = ((closingPrice-prevEmaValue) * multiplier) + prevEmaValue;
			shortMAPriceList.add(round(currentEmaValue,2));

			// Push ema to client specifying that it is for longMA

			lastShortPlotID++;
			lastShortEMAExecutedID++;
		}
		else{
			prevEmaValue = longMAPriceList.get(lastLongPlotID);
			closingPrice = originalPriceList.get(lastLongEMAExecutedID + 1);
			currentPrice = closingPrice;
			currentEmaValue = ((closingPrice-prevEmaValue) * multiplier) + prevEmaValue;
			longMAPriceList.add(round(currentEmaValue,2));

			//Push ema to client specifying that it is for longMA

			lastLongPlotID++;
			lastLongEMAExecutedID++;
		}	
	}


	public Float round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (float) tmp / factor;
	}

	public void evaluate(){

		if(shortMAPriceList.get(lastShortPlotID) >= longMAPriceList.get(lastLongPlotID)){

			if(shortMAisHigher == true){
				tradeDecision.add(neutral);
			}
			else if(shortMAisHigher == false){
				tradeDecision.add(buy);

				buyPrice = originalPriceList.get(lastLongEMAExecutedID);
				shortMAisHigher = true;
				
				
				if (!hasStocks){
					//TODO: Execute a BUY
					tradeManagerBean.persistTradesPreApproval(portfolioId, stockName, "BUY", strategy, quantityBoughtOrSold, buyPrice);
					
					String buyPriceStr = Float.toString(buyPrice);
					String maxShares = ""+quantityBoughtOrSold;
					
					try {
						tradeSendBean.runSender("true", buyPriceStr, maxShares, stockName);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					hasStocks = true;
				}
				System.out.println("EXECUTES BUY: LongMA: "+String.valueOf(longMAPriceList.get(lastLongPlotID)) 
						+"shortMA: "+ String.valueOf(shortMAPriceList.get(lastShortPlotID))
						+"buyPrice: "+ String.valueOf(buyPrice));
			}
		}
		else{

			if(shortMAisHigher == true){
				tradeDecision.add(sell);

				

				shortMAisHigher = false;
				if(hasStocks) {
					//TODO: Execute SELL;
					System.out.println("EXECUTES SELL: LongMA: "+String.valueOf(longMAPriceList.get(lastLongPlotID)) 
					+" shortMA: "+ String.valueOf(shortMAPriceList.get(lastShortPlotID))
					+" sellPrice: "+ String.valueOf(originalPriceList.get(lastLongEMAExecutedID)));
					
					tradeManagerBean.persistTradesPreApproval(portfolioId, stockName, "SELL", strategy, quantityBoughtOrSold, buyPrice);
					String sellPriceStr = String.valueOf(originalPriceList.get(lastLongEMAExecutedID));
					String maxShares = ""+quantityBoughtOrSold;

					try {
						tradeSendBean.runSender("false", sellPriceStr, maxShares, stockName);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					hasStocks = false;
				}
				
			}
			else if(shortMAisHigher == false){
				tradeDecision.add(neutral);
			}

		}

	}

	// To provide timePeriod in minutes
	public Float simpleMovingAverage(float shortMA2, List<Float> originalPriceList2){

		int length = (int)(shortMA2 / dataTimePeriod);
		double closingPrice;
		double sum = 0;

		for(int i=0; i<length; i++){
			closingPrice = (originalPriceList2.get(i));
			sum += closingPrice;
		}

		return (float) (sum/length);
	}


	public double getCurrentPrice(){
		return this.currentPrice;
	}

	public double getBuyPrice(){
		return this.buyPrice;
	}

	public boolean hasStocks(){
		return this.hasStocks;
	}

	public void setHasStocks(boolean hasStock){
		this.hasStocks = hasStock;
	}

	public boolean getStart(){
		return this.start;
	}

	public void setStart(boolean start){
		this.start = start;
	}

	public boolean getPause(){
		return this.pause;
	}

	public void setPause(boolean pause){
		this.pause = pause;
	}

	public boolean getStop(){
		return this.stop;
	}

	public void setStop(boolean stop){
		this.stop = stop;
	}
	
	public void setParams(int portfolioId, String ticker, float shortMa, float longMa, int quantityBoughtOrSold){
		this.portfolioId = portfolioId;
		this.stockName=ticker;
		this.shortMA = shortMa;
		this.longMA = longMa;
		this.quantityBoughtOrSold = quantityBoughtOrSold;
		
		this.dataTimePeriod = 1; // Every 1 second one stock data
		this.lastShortEMAExecutedID = 0;
		this.lastLongEMAExecutedID = 0;
		this.lastShortPlotID = 0;
		this.lastLongPlotID = 0;
		this.shortSMAexecuted = false;
		this.longSMAexecuted = false;
		this.lastRecordedID = 0;	
		this.hasStocks = false;
		this.start = false;
		this.stop = false;
		this.pause = false;
		
		/*File file = new File("outputTMA.txt");
		FileOutputStream fos;
		try{
			fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			System.setOut(ps);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
	}
}
