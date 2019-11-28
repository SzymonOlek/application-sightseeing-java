package com.project.sightseeing.User;

public class User {
	
	private String login;
	private String passwd;
	
	
	
	public User() {
		super();
		this.login = null;
		this.passwd = null;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getLogin() {
		return login;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
}
