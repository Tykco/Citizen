package com.citi.citizen_app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the historicalmarketdata database table.
 * 
 */
@Entity
@Table(name="historicalmarketdata")
@NamedQuery(name="Historicalmarketdata.findAll", query="SELECT h FROM Historicalmarketdata h")
public class Historicalmarketdata implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private float close;
	private String date;
	private String epochTimestamp;
	private float high;
	private float low;
	private float open;
	private BigInteger volume;
	private Stock stock;

	public Historicalmarketdata() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="close_", nullable=false)
	public float getClose() {
		return this.close;
	}

	public void setClose(float close) {
		this.close = close;
	}


	@Column(name="date_", length=8)
	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}


	@Column(name="epoch_timestamp", length=10)
	public String getEpochTimestamp() {
		return this.epochTimestamp;
	}

	public void setEpochTimestamp(String epochTimestamp) {
		this.epochTimestamp = epochTimestamp;
	}


	@Column(nullable=false)
	public float getHigh() {
		return this.high;
	}

	public void setHigh(float high) {
		this.high = high;
	}


	@Column(nullable=false)
	public float getLow() {
		return this.low;
	}

	public void setLow(float low) {
		this.low = low;
	}


	@Column(name="open_", nullable=false)
	public float getOpen() {
		return this.open;
	}

	public void setOpen(float open) {
		this.open = open;
	}


	@Column(nullable=false)
	public BigInteger getVolume() {
		return this.volume;
	}

	public void setVolume(BigInteger volume) {
		this.volume = volume;
	}


	//uni-directional many-to-one association to Stock
	@ManyToOne
	@JoinColumn(name="stock_id", nullable=false)
	public Stock getStock() {
		return this.stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

}