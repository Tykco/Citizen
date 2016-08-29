package citi.zen.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigInteger;
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
	private BigInteger profitLoss;
	private BigInteger startBalance;
	private User user;
	private List<Position> positions;

	public Portfolio() {
	}


	@Id
	@Column(name="portfolio_id")
	public int getPortfolioId() {
		return this.portfolioId;
	}

	public void setPortfolioId(int portfolioId) {
		this.portfolioId = portfolioId;
	}


	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}


	public Timestamp getModified() {
		return this.modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}


	@Column(name="profit_loss")
	public BigInteger getProfitLoss() {
		return this.profitLoss;
	}

	public void setProfitLoss(BigInteger profitLoss) {
		this.profitLoss = profitLoss;
	}


	@Column(name="start_balance")
	public BigInteger getStartBalance() {
		return this.startBalance;
	}

	public void setStartBalance(BigInteger startBalance) {
		this.startBalance = startBalance;
	}


	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id")
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

}