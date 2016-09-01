package com.citi.citizen_app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the portfolios database table.
 * 
 */
@Entity
@Table(name="portfolios")
@NamedQuery(name="Portfolio.findAll", query="SELECT p FROM Portfolio p")
public class Portfolio implements Serializable {
	private static final long serialVersionUID = 1L;
	private int portfolioId;
	private Timestamp created;
	private Timestamp modified;
	private BigDecimal pfProfitLoss;
	private BigDecimal startBalance;
	private User user;
	private List<Position> positions;
	private List<Trade> trades;

	public Portfolio() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="portfolio_id", unique=true, nullable=false)
	public int getPortfolioId() {
		return this.portfolioId;
	}

	public void setPortfolioId(int portfolioId) {
		this.portfolioId = portfolioId;
	}


	@Column(nullable=false)
	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}


	@Column(nullable=false)
	public Timestamp getModified() {
		return this.modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}


	@Column(name="pf_profit_loss", precision=10, scale=2)
	public BigDecimal getPfProfitLoss() {
		return this.pfProfitLoss;
	}

	public void setPfProfitLoss(BigDecimal pfProfitLoss) {
		this.pfProfitLoss = pfProfitLoss;
	}


	@Column(name="start_balance", precision=10, scale=2)
	public BigDecimal getStartBalance() {
		return this.startBalance;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}


	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	//bi-directional many-to-one association to Position
	@OneToMany(mappedBy="portfolio")
	public List<Position> getPositions() {
		return this.positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public Position addPosition(Position position) {
		getPositions().add(position);
		position.setPortfolio(this);

		return position;
	}

	public Position removePosition(Position position) {
		getPositions().remove(position);
		position.setPortfolio(null);

		return position;
	}


	//bi-directional many-to-one association to Trade
	@OneToMany(mappedBy="portfolio")
	public List<Trade> getTrades() {
		return this.trades;
	}

	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

	public Trade addTrade(Trade trade) {
		getTrades().add(trade);
		trade.setPortfolio(this);

		return trade;
	}

	public Trade removeTrade(Trade trade) {
		getTrades().remove(trade);
		trade.setPortfolio(null);

		return trade;
	}

}