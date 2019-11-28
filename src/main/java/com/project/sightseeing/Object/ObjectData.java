package com.project.sightseeing.Object;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "object")
public class ObjectData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Integer object_id;
	private String localization;
	private String object_name;
	private String description;
	private Integer city_id;
	
	public void setObject_id(Integer object_id) {
		this.city_id = object_id;
	}
	
	public Integer getObject_id() {
		return object_id;
	}
	
	public void setLocalization(String localization) {
		this.localization = localization;
	}
	
	public String getLocalization() {
		return localization;
	}
	
	public void setObject_name(String object_name) {
		this.object_name = object_name;
	}
	
	public String geyObject_name() {
		return object_name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	
	public Integer getCity_id() {
		return city_id;
	}
}
