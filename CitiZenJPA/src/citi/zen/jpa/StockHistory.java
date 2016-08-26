package citi.zen.jpa;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="stock_history")
public class StockHistory implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int historyId;
	private String stockName;
	private double ask;
	private double bid;
	private LocalDateTime timeRecord;
	
	public StockHistory() {}
	
	public StockHistory(String stockName, double ask, double bid) {
		this.stockName = stockName;
		this.ask = ask;
		this.bid = bid;
		this.timeRecord = LocalDateTime.now();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getHistoryId() {
		return historyId;
	}
	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}
	
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public double getAsk() {
		return ask;
	}
	public void setAsk(double ask) {
		this.ask = ask;
	}
	public double getBid() {
		return bid;
	}
	public void setBid(double bid) {
		this.bid = bid;
	}
	public LocalDateTime getTimeRecord() {
		return timeRecord;
	}
	public void setTimeRecord(LocalDateTime timeRecord) {
		this.timeRecord = timeRecord;
	}
	
	
}
