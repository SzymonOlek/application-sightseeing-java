package com.project.sightseeing.Ban;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "ban")
public class BanData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Integer ban_id;
	private Integer admin_id;
	private Integer user_id;
	@Enumerated(EnumType.STRING)
	private Ban_type ban_type;
	private String date_since;
	private String date_by;
	
	public void setBan_id(Integer ban_id) {
		this.ban_id = ban_id;
	}
	
	public Integer getBan_id() {
		return ban_id;
	}
	
	public void setAdmin_id(Integer admin_id) {
		this.admin_id = admin_id;
	}
	
	public Integer getAdmin_id() {
		return admin_id;
	}
	
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	public Integer getUser_id() {
		return user_id;
	}

	public void setBan_type(Ban_type ban_type) {
		this.ban_type = (ban_type);
	}
	
	public Ban_type getBan_type() {
		return ban_type;
	}
	
	public void setDate_since(String date_since) {
		this.date_since = date_since;
	}
	
	public String getDate_since() {
		return date_since;
	}
	
	public void setDate_by(String date_by) {
		this.date_by = date_by;
	}
	
	public String getDate_by() {
		return date_by;
	}
	
	private Ban_type toBan_type(String ban_type) {
	
		if ( ban_type.equals(Ban_type.coment_ban.toString())){
			return Ban_type.coment_ban; 
		}
		if (ban_type.equals(Ban_type.perma_ban.toString())) {
			return Ban_type.perma_ban;
		}
		return null;
	}
}
