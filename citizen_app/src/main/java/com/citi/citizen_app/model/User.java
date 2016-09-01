package com.citi.citizen_app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private int userId;
	private Timestamp created;
	private String email;
	private String firstName;
	private String lastName;
	private Timestamp modified;
	private String username;
	private List<Portfolio> portfolios;

	public User() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id", unique=true, nullable=false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	@Column(nullable=false)
	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}


	@Column(length=255)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name="first_name", nullable=false, length=255)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	@Column(name="last_name", nullable=false, length=255)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	@Column(nullable=false)
	public Timestamp getModified() {
		return this.modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}


	@Column(nullable=false, length=255)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	//bi-directional many-to-one association to Portfolio
	@OneToMany(mappedBy="user")
	public List<Portfolio> getPortfolios() {
		return this.portfolios;
	}

	public void setPortfolios(List<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}

	public Portfolio addPortfolio(Portfolio portfolio) {
		getPortfolios().add(portfolio);
		portfolio.setUser(this);

		return portfolio;
	}

	public Portfolio removePortfolio(Portfolio portfolio) {
		getPortfolios().remove(portfolio);
		portfolio.setUser(null);

		return portfolio;
	}

}