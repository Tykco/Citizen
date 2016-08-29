package citi.zen.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the historicalmarketdata_monthyear database table.
 * 
 */
@Entity
@Table(name="historicalmarketdata_monthyear")
@NamedQuery(name="HistoricalmarketdataMonthyear.findAll", query="SELECT h FROM HistoricalmarketdataMonthyear h")
public class HistoricalmarketdataMonthyear implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private float close;
	private String date;
	private float high;
	private float low;
	private float open;
	private int stockId;
	private BigInteger volume;

	public HistoricalmarketdataMonthyear() {
	}


	@Id
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="close_")
	public float getClose() {
		return this.close;
	}

	public void setClose(float close) {
		this.close = close;
	}


	@Column(name="date_")
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}


	public float getHigh() {
		return this.high;
	}

	public void setHigh(float high) {
		this.high = high;
	}


	public float getLow() {
		return this.low;
	}

	public void setLow(float low) {
		this.low = low;
	}


	@Column(name="open_")
	public float getOpen() {
		return this.open;
	}

	public void setOpen(float open) {
		this.open = open;
	}


	@Column(name="stock_id")
	public int getStockId() {
		return this.stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}


	public BigInteger getVolume() {
		return this.volume;
	}

	public void setVolume(BigInteger volume) {
		this.volume = volume;
	}

}