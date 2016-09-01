package com.citi.citizen_app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the trades database table.
 * 
 */
@Entity
@Table(name="trades")
@NamedQuery(name="Trade.findAll", query="SELECT t FROM Trade t")
public class Trade implements Serializable {
	private static final long serialVersionUID = 1L;
	private int tradeId;
	private String approved;
	private String buySell;
	private float price;
	private int quantity;
	private String strategy;
	private Timestamp timePurchased;
	private Portfolio portfolio;
	private Stock stock;

	public Trade() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="trade_id", unique=true, nullable=false)
	public int getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}


	@Column(nullable=false, length=10)
	public String getApproved() {
		return this.approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}


	@Column(name="buy_sell", nullable=false, length=4)
	public String getBuySell() {
		return this.buySell;
	}

	public void setBuySell(String buySell) {
		this.buySell = buySell;
	}


	@Column(nullable=false)
	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}


	@Column(nullable=false)
	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	@Column(nullable=false, length=255)
	public String getStrategy() {
		return this.strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}


	@Column(name="time_purchased", nullable=false)
	public Timestamp getTimePurchased() {
		return this.timePurchased;
	}

	public void setTimePurchased(Timestamp timePurchased) {
		this.timePurchased = timePurchased;
	}


	//bi-directional many-to-one association to Portfolio
	@ManyToOne
	@JoinColumn(name="portfolio_id", nullable=false)
	public Portfolio getPortfolio() {
		return this.portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
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