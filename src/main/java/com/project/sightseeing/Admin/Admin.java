package com.project.sightseeing.Admin;

import com.project.sightseeing.User.*;

public class Admin extends User{
	
	private AdminData adminData;
	
	public Admin() {
		this.adminData = null;
	}
	
	public Admin(AdminData adminData) {
		this.adminData = adminData;
	}
	
	public void setAdminData (AdminData adminData) {
		this.adminData = adminData;
	}
	
	public AdminData getAdminData() {
		return adminData;
	}
}
