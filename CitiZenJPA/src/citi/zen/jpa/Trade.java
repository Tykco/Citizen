package citi.zen.jpa;

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
	private byte approved;
	private String buySell;
	private float price;
	private int quantity;
	private int stockId;
	private String strategy;
	private Timestamp timePurchased;

	public Trade() {
	}


	@Id
	@Column(name="trade_id")
	public int getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}


	public byte getApproved() {
		return this.approved;
	}

	public void setApproved(byte approved) {
		this.approved = approved;
	}


	@Column(name="buy_sell")
	public String getBuySell() {
		return this.buySell;
	}

	public void setBuySell(String buySell) {
		this.buySell = buySell;
	}


	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}


	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	@Column(name="stock_id")
	public int getStockId() {
		return this.stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}


	public String getStrategy() {
		return this.strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}


	@Column(name="time_purchased")
	public Timestamp getTimePurchased() {
		return this.timePurchased;
	}

	public void setTimePurchased(Timestamp timePurchased) {
		this.timePurchased = timePurchased;
	}

}