package citi.zen.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigInteger;


/**
 * The persistent class for the livemarketdata database table.
 * 
 */
@Entity
@NamedQuery(name="Livemarketdata.findAll", query="SELECT l FROM Livemarketdata l")
public class Livemarketdata implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private float ask;
	private float bid;
	private Timestamp created;
	private float open;
	private float price;
	private int stockId;
	private BigInteger volume;

	public Livemarketdata() {
	}


	@Id
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public float getAsk() {
		return this.ask;
	}

	public void setAsk(float ask) {
		this.ask = ask;
	}


	public float getBid() {
		return this.bid;
	}

	public void setBid(float bid) {
		this.bid = bid;
	}


	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}


	@Column(name="open_")
	public float getOpen() {
		return this.open;
	}

	public void setOpen(float open) {
		this.open = open;
	}


	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
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