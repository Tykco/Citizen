package citi.zen.jpa;

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
	@Column(name="user_id")
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name="first_name")
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	@Column(name="last_name")
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public Timestamp getModified() {
		return this.modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}


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