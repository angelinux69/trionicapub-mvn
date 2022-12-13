package it.trionica.web.model.dto.user;

import java.util.List;

import lombok.ToString;

@ToString(includeFieldNames=true)
public class LoginUser {
	
	private Long id;
	private String username;
	private String nome;
	private String cognome;
	private String email;
	private String jwt;
	private String password;
	private List<String> roles;
	
	public LoginUser(Long id, String username, String nome, String cognome, String email, String jwt, String password,
			List<String> roles) {
		super();
		this.id = id;
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.jwt = jwt;
		this.password = password;
		this.roles = roles;
	}
	
	public LoginUser() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
