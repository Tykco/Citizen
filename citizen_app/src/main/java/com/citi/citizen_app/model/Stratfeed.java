package com.citi.citizen_app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the stratfeed database table.
 * 
 */
@Entity
@Table(name="stratfeed")
@NamedQuery(name="Stratfeed.findAll", query="SELECT s FROM Stratfeed s")
public class Stratfeed implements Serializable {
	private static final long serialVersionUID = 1L;
	private int stratfeedId;
	private Timestamp created;
	private float longMa;
	private float lowerBand;
	private float middleBand;
	private float shortMa;
	private String strategy;
	private float upperBand;
	private Livemarketdata livemarketdata;

	public Stratfeed() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="stratfeed_id", unique=true, nullable=false)
	public int getStratfeedId() {
		return this.stratfeedId;
	}

	public void setStratfeedId(int stratfeedId) {
		this.stratfeedId = stratfeedId;
	}


	@Column(nullable=false)
	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}


	@Column(name="long_ma")
	public float getLongMa() {
		return this.longMa;
	}

	public void setLongMa(float longMa) {
		this.longMa = longMa;
	}


	@Column(name="lower_band")
	public float getLowerBand() {
		return this.lowerBand;
	}

	public void setLowerBand(float lowerBand) {
		this.lowerBand = lowerBand;
	}


	@Column(name="middle_band")
	public float getMiddleBand() {
		return this.middleBand;
	}

	public void setMiddleBand(float middleBand) {
		this.middleBand = middleBand;
	}


	@Column(name="short_ma")
	public float getShortMa() {
		return this.shortMa;
	}

	public void setShortMa(float shortMa) {
		this.shortMa = shortMa;
	}


	@Column(nullable=false, length=255)
	public String getStrategy() {
		return this.strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}


	@Column(name="upper_band")
	public float getUpperBand() {
		return this.upperBand;
	}

	public void setUpperBand(float upperBand) {
		this.upperBand = upperBand;
	}


	//uni-directional many-to-one association to Livemarketdata
	@ManyToOne
	@JoinColumn(name="live_id", nullable=false)
	public Livemarketdata getLivemarketdata() {
		return this.livemarketdata;
	}

	public void setLivemarketdata(Livemarketdata livemarketdata) {
		this.livemarketdata = livemarketdata;
	}

}