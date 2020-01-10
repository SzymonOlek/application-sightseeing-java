package com.project.sightseeing.Commentary;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "commentary")
public class CommentaryData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer commentary_id;
	private Integer user_id;
	private Integer object_id;
	private String contents;
	private String comment_date;
	@Enumerated(EnumType.STRING)
	private Rate rate;
	
	public void setCommentary_id(Integer commentary_id) {
		this.commentary_id = commentary_id;
	}
	
	public Integer getCommentary_id() {
		return commentary_id;
	}
	
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	public Integer getUser_id() {
		return user_id;
	}
	
	public void setObject_id(Integer object_id) {
		this.object_id = object_id;
	}
	
	public Integer getObject_id() {
		return object_id;
	}
	
	public void setContents(String contents) {
		this.contents  = contents;
	}
	
	public String getContents() {
		return contents;
	}
	
	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}
	
	public String getComment_date() {
		return comment_date;
	}
	
	public void setRate(Rate rate) {
		this.rate = rate;
	}
	
	public Rate getRate() {
		return rate;
	}
}
