package com.project.sightseeing.Route;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "route")
public class RouteData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer route_id;
	private Integer object_1_id;
	private Integer object_2_id;
	private Integer distance;
	private Integer city_id;
	
	public void setRoute_id(Integer route_id) {
		this.route_id = route_id;
	}
	
	public Integer getRoute_id() {
		return route_id;
	}
	
	public void setObject_1_id(Integer object_1_id) {
		this.object_1_id = object_1_id;
	}
	
	public Integer getObject_1_id() {
		return object_1_id;
	}
	
	public void setObject_2_id(Integer object_2_id) {
		this.object_2_id = object_2_id;
	}
	
	public Integer getObject_2_id() {
		return object_2_id;
	}
	
	public void setDistance(Integer distane) {
		this.distance = distane;
	}
	
	public Integer getDistance() {
		return distance;
	}
	
	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	
	public Integer getCity_id() {
		return city_id;
	}
}
