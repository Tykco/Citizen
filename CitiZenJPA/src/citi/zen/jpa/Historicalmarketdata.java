package citi.zen.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the historicalmarketdata database table.
 * 
 */
@Entity
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


	@Column(name="epoch_timestamp")
	public String getEpochTimestamp() {
		return this.epochTimestamp;
	}

	public void setEpochTimestamp(String epochTimestamp) {
		this.epochTimestamp = epochTimestamp;
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


	public BigInteger getVolume() {
		return this.volume;
	}

	public void setVolume(BigInteger volume) {
		this.volume = volume;
	}


	//uni-directional many-to-one association to Stock
	@ManyToOne
	@JoinColumn(name="stock_id")
	public Stock getStock() {
		return this.stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

}