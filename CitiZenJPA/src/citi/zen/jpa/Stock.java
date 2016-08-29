package citi.zen.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the stocks database table.
 * 
 */
@Entity
@Table(name="stocks")
@NamedQuery(name="Stock.findAll", query="SELECT s FROM Stock s")
public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;
	private int stockId;
	private String companyName;
	private String currency;
	private String exchangeName;
	private String ticker;
	private String timezone;
	//private List<Position> positions;

	public Stock() {
	}


	@Id
	@Column(name="stock_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getStockId() {
		return this.stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}


	@Column(name="company_name")
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}


	@Column(name="exchange_name")
	public String getExchangeName() {
		return this.exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}


	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}


	public String getTimezone() {
		return this.timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}


	/*//bi-directional many-to-one association to Position
	@OneToMany(mappedBy="stock")
	public List<Position> getPositions() {
		return this.positions;
	}
	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
	public Position addPosition(Position position) {
		getPositions().add(position);
		position.setStock(this);
		return position;
	}
	public Position removePosition(Position position) {
		getPositions().remove(position);
		position.setStock(null);
		return position;
	}*/

}