package com.citi.citizen_app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the positions database table.
 * 
 */
@Entity
@Table(name="positions")
@NamedQuery(name="Position.findAll", query="SELECT p FROM Position p")
public class Position implements Serializable {
	private static final long serialVersionUID = 1L;
	private int positionId;
	private int shares;
	private BigDecimal sharesProfitLoss;
	private Portfolio portfolio;
	private Stock stock;

	public Position() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="position_id", unique=true, nullable=false)
	public int getPositionId() {
		return this.positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}


	@Column(nullable=false)
	public int getShares() {
		return this.shares;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}


	@Column(name="shares_profit_loss", nullable=false, precision=10, scale=2)
	public BigDecimal getSharesProfitLoss() {
		return this.sharesProfitLoss;
	}

	public void setSharesProfitLoss(BigDecimal sharesProfitLoss) {
		this.sharesProfitLoss = sharesProfitLoss;
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