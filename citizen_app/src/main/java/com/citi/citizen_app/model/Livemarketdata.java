package com.citi.citizen_app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the livemarketdata database table.
 * 
 */
@Entity
@Table(name="livemarketdata")
@NamedQuery(name="Livemarketdata.findAll", query="SELECT l FROM Livemarketdata l")
public class Livemarketdata implements Serializable {
	private static final long serialVersionUID = 1L;
	private int liveId;
	private float ask;
	private float bid;
	private Timestamp created;
	private float open;
	private float price;
	private Stock stock;

	public Livemarketdata() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="live_id", unique=true, nullable=false)
	public int getLiveId() {
		return this.liveId;
	}

	public void setLiveId(int liveId) {
		this.liveId = liveId;
	}


	@Column(nullable=false)
	public float getAsk() {
		return this.ask;
	}

	public void setAsk(float ask) {
		this.ask = ask;
	}


	@Column(nullable=false)
	public float getBid() {
		return this.bid;
	}

	public void setBid(float bid) {
		this.bid = bid;
	}


	@Column(nullable=false)
	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}


	@Column(name="open_", nullable=false)
	public float getOpen() {
		return this.open;
	}

	public void setOpen(float open) {
		this.open = open;
	}


	@Column(nullable=false)
	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
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