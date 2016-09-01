package com.citi.citizen_app.data.strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;

import com.citi.citizen_app.data.repository.EJB.RepositoryLiveDataBean;
import com.citi.citizen_app.data.repository.EJB.RepositoryStratFeedDataBean;
import com.citi.citizen_app.model.Livemarketdata;

@Stateful
public class BollingerBandStrategyBean {

	@Inject
	private RepositoryLiveDataBean liveDataBean;
	
	@Inject
	private RepositoryStratFeedDataBean repoStratFeedBean;

	private int dataTimePeriod;
	private float ma;
	private int lastExecutedID, lastRecordedID, lastBandID, originalID;
	private int maxShares;
	private float threshold;
	private final String strategy = "BB";

	private float stdDiv;

	private String stockName;

	private float buyPrice, currentPrice;

	private boolean hasEnoughStocks, hasStocks;

	private List<Float> originalPriceList = new ArrayList<Float>();

	private List<Float> middleBandList = new ArrayList<Float>();
	private List<Float> upperBandList = new ArrayList<Float>();
	private List<Float> lowerBandList = new ArrayList<Float>();

	public BollingerBandStrategyBean() {

	}

	public void startBbStrategy(String parameters) {

	}

	public void executeStrategy() {

		if(!hasEnoughStocks)
			populateFirst();
		else{

			//Keep querying for data from database

			/*	TO/DO: SELECT ticker, price from table 
			WHERE ticker = "IBM" and id > lastRecordID 
			 */

			List<Livemarketdata> liveDataList = liveDataBean.getLiveDataByTickerIdConstraint(stockName,lastRecordedID);
			System.out.println("size: " + liveDataList.size());
			for (Livemarketdata ld : liveDataList) {
				// TO/DO: Loop through the query resultset, add into the queue
				lastRecordedID = ld.getLiveId();
				originalPriceList.add(ld.getPrice());

				//Calculate Simple Moving Average 
				float sma = simpleMovingAverage(ma, originalPriceList, lastExecutedID);
				middleBandList.add(sma);

				//Calculate Standard Deviation
				float sd = calculateStandardDeviation(ma, originalPriceList, lastExecutedID);
				lowerBandList.add(sma-sd);
				upperBandList.add(sma+sd);
				//TO/DO: Push sma to client the lower, middle and upper band
				//upperBandList.get(lastExecutedID + 1), lowerBandList.get(lastExecutedID + 1), middleBandList.get(lastExecutedID + 1)
				//originalPriceList.get(lastExecutedID -1), lowerBandList.get(lastBandID), upperBandList.get(lastBandID), middleBandList.get(lastBandID)
				
				System.out.println("BB PRICE: "+ originalPriceList.get(lastExecutedID-1)
										+ " Lower: "+ String.valueOf(lowerBandList.get(lastBandID)) 
										+ " UPPER: " + String.valueOf(upperBandList.get(lastBandID)) 
										+ " MIDDLE: " + String.valueOf(middleBandList.get(lastBandID)));
				repoStratFeedBean.persistStratFeedBbDataBands(lowerBandList.get(lastBandID), upperBandList.get(lastBandID), middleBandList.get(lastBandID), lastRecordedID);

				if(middleBandList.size() > 0){
					// Evaluate trade decision
					if(upperBandList.get(lastBandID) < originalPriceList.get((int) (lastBandID+(ma/dataTimePeriod)-1))){
						float sellPrice = originalPriceList.get((int) (lastBandID+(ma/dataTimePeriod)-1));
						
						System.out.println("\nBB EXECUTES SELL: @PRICE: " + originalPriceList.get((int) (lastBandID+(ma/dataTimePeriod)-1))
						+" LOWER: "+ String.valueOf(lowerBandList.get(lastBandID)) 
						+ " UPPER: " + String.valueOf(upperBandList.get(lastBandID)) 
						+ " MIDDLE: " + String.valueOf(middleBandList.get(lastBandID))+"\n");
						
						if(hasStocks) {
							//TODO: Execute sell(maxShares, sellPrice);
							hasStocks = false;
						}
						
					}
					else if(lowerBandList.get(lastBandID) > originalPriceList.get((int) (lastBandID+(ma/dataTimePeriod)-1))){
						//TO/DO: Execute buy
						buyPrice = originalPriceList.get((int) (lastBandID+(ma/dataTimePeriod)-1));
						System.out.println("\nBB EXECUTES BUY: "+" @PRICE: " + buyPrice
						+ " LOWER: "+ String.valueOf(lowerBandList.get(lastBandID)) 
						+ " UPPER: " + String.valueOf(upperBandList.get(lastBandID)) 
						+ " MIDDLE: " + String.valueOf(middleBandList.get(lastBandID))+"\n");
						
						if (!hasStocks){
							//TODO: Execute  buy(maxShares, buyPrice);
							hasStocks = true;
						}
						
						
					}
					lastBandID++;
					System.out.println("LAST BAND ID: "+ lastBandID);
				}
				this.lastExecutedID++;
			}
		}
	}

	public void populateFirst(){

		//Keep querying for data from database

		/*	TO/DO: SELECT ticker, price from table 
		WHERE ticker = "IBM" and id > lastRecordedID 
		 */
		List<Livemarketdata> liveDataList = liveDataBean.getLiveDataByTickerIdConstraint(stockName,lastRecordedID);
		System.out.println("size: " + liveDataList.size());
		for (Livemarketdata ld : liveDataList) {
			// TO/DO: Loop through the query resultset, add the key and values into the hashmap
			lastRecordedID = ld.getLiveId();
			originalPriceList.add(ld.getPrice());


			// TO/DO: Push upperband, lowerband, middleband, price to client. Only price has value
			//originalPriceList.get(originalID);
			System.out.println("BB PERSIST FIRST price: " + String.valueOf(ld.getPrice()));
			repoStratFeedBean.persistStratFeedBbDataPrice(lastRecordedID);

			if(originalPriceList.size() == (ma/dataTimePeriod)){
				lastExecutedID = (int) ma/dataTimePeriod - 1;
				hasEnoughStocks = true;
				return; // return while still within the resultset

			}
		}
	}

	// To provide timePeriod in minutes
	public float simpleMovingAverage(float timePeriod, List<Float> originalPriceList, int lastExecutedID){

		int length = (int) (timePeriod / dataTimePeriod);
		float closingPrice;
		float sum = 0;

		for(int i= (lastExecutedID-length+1); i<=lastExecutedID; i++){
			closingPrice = (originalPriceList.get(i));
			sum += closingPrice;
			this.currentPrice = closingPrice;
		}

		System.out.println("IN SMA METHOD: LASTEXECUTED ID: "+ lastExecutedID);
		System.out.println("IN SMA METHOD: SUM/LENGTH: "+ sum/length);
		return sum/length;
	}

	public float calculateStandardDeviation(float timePeriod, List<Float> originalPriceList, int lastExecutedID){

		int length = (int) (timePeriod / dataTimePeriod);
		float closingPrice;
		float sum = 0;

		for(int i= (lastExecutedID-length+1); i<=lastExecutedID; i++){
			closingPrice = (originalPriceList.get(i));
			sum += closingPrice;
		}

		float average = sum/length;

		float varianceBeforeDivide = 0;

		float difference;

		for(int i= (lastExecutedID-length+1); i<=lastExecutedID; i++){
			difference = originalPriceList.get(i) - average;
			varianceBeforeDivide += Math.pow(difference, 2.0);
		}

		float variance = varianceBeforeDivide / length;

		System.out.println("IN CALCULATE STDDIV METHOD: lastExecutedID: "+ lastExecutedID);
		System.out.println("IN CALCULATE STDDIV METHOD: Math.sqrt(variance): "+ (float) Math.sqrt(variance));
		return (float) Math.sqrt(variance);
	}


	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public boolean hasStocks(){
		return this.hasStocks;
	}

	public void setHasStocks(boolean hasStocks){
		this.hasStocks = hasStocks;
	}

	public double getCurrentPrice(){
		return this.currentPrice;
	}

	public double getBuyPrice(){
		return this.buyPrice;
	}

	public void setParams(float ma, int maxShares, float threshold, float stdDiv, String ticker) {
		this.ma = ma;
		this.maxShares = maxShares;
		this.threshold = threshold;
		this.stockName = ticker;

		this.dataTimePeriod = 1; // Every 1 second one stock data
		this.hasEnoughStocks = false;
		this.lastRecordedID = 0;
		this.stdDiv = stdDiv;
		this.lastBandID = 0;
		this.hasStocks = false;

		File file = new File("outputBB3.txt");
		FileOutputStream fos;
		try{
			fos = new FileOutputStream(file);
			PrintStream ps = new PrintStream(fos);
			System.setOut(ps);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
