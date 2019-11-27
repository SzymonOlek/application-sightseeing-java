package com.project.sightseeing.Photo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "photo")
public class PhotoData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer photo_id;
	private Integer object_id;
	private String photo_path;
	
	public void setPhoto_id(Integer photo_id) {
		this.photo_id = photo_id;
	}
	
	public Integer getPhoto_id() {
		return photo_id;
	}
	
	public void setObject_id(Integer object_id) {
		this.object_id = object_id;
	}
	
	public Integer getObject_id() {
		return object_id;
	}
	
	public void setPhoto_path(String photo_path) {
		this.photo_path = photo_path;
	}
	
	public String getPhoto_path() {
		return photo_path;
	}
}
