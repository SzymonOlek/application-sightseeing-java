package com.project.sightseeing.Sysuser;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.sightseeing.Admin.AdminData;
import com.project.sightseeing.User.*;

public class Sysuser extends User{
	
	
	private SysuserData sysuserData;
	
	public Sysuser() {
		this.sysuserData = null;
	}
	
	public Sysuser(SysuserData sysuserData) {
		this.sysuserData = sysuserData;
	}
	
	public void setSysuserData (SysuserData sysuserData) {
		this.sysuserData = sysuserData;
	}
	
	public SysuserData getSysuserData() {
		return sysuserData;
	}
}