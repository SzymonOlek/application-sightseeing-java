package com.project.sightseeing.User;

import java.util.Map;

import org.springframework.web.context.request.RequestContextHolder;

import com.project.sightseeing.SightseeingApplication;
import com.project.sightseeing.Admin.Admin;
import com.project.sightseeing.Sysuser.Sysuser;

public class User {
	
	private String login;
	private String passwd;
	
	
	
	public User() {
		super();
		this.login = null;
		this.passwd = null;
	}

	public String isLogged() {
		
		String session = RequestContextHolder.currentRequestAttributes().getSessionId();
		
		for (Map.Entry<String, User> entry : SightseeingApplication.loggedInUsers.entrySet()) {
			if (entry.getKey().equals(session)) {
				if (entry.getValue() instanceof Admin) {
					return "a";
				} else if (entry.getValue() instanceof Sysuser) {
					return "u";
				}
			}
		}
		return "g";
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
