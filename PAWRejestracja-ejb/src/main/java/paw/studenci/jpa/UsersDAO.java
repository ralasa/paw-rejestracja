package paw.studenci.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name = "users", schema = "rafal")
@NamedQuery(name="Users.findAll", query="SELECT u FROM UsersDAO u")
public class UsersDAO implements Serializable {
	private static final long serialVersionUID = 1739286409403189181L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String password;

	private String username;

	public UsersDAO() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

}