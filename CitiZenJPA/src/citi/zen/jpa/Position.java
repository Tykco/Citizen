package citi.zen.jpa;

import java.io.Serializable;
import javax.persistence.*;


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
	private Portfolio portfolio;
	private Stock stock;

	public Position() {
	}


	@Id
	@Column(name="position_id")
	public int getPositionId() {
		return this.positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}


	public int getShares() {
		return this.shares;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}


	//bi-directional many-to-one association to Portfolio
	@ManyToOne
	@JoinColumn(name="portfolio_id")
	public Portfolio getPortfolio() {
		return this.portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}


	//bi-directional many-to-one association to Stock
	@ManyToOne
	@JoinColumn(name="stock_id")
	public Stock getStock() {
		return this.stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

}