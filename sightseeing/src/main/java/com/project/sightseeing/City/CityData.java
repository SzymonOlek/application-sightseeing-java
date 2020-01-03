package com.project.sightseeing.City;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city")
public class CityData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Integer city_id; 
	private String city_name;
	private Integer obj_quan;
	
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	
	public Integer getCity_id() {
		return city_id;
	}
	
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
	public String getCity_name() {
		return city_name;
	}
	
	public void setObj_quan(Integer obj_quan) {
		this.obj_quan = obj_quan;
	}
	
	public Integer getObj_quan() {
		return obj_quan;
	}
}
