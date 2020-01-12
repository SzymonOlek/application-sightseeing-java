package com.project.sightseeing.Object;

import java.util.ArrayList;

import com.project.sightseeing.Others.Values;

public class WraperInteger {

	ArrayList<Values> distanceToOtherObj= new ArrayList<Values>();
	ArrayList<ObjectData> otherObjectsInCity= new ArrayList<ObjectData>();

	
	public void addValues(Values v ) {
		
		this.distanceToOtherObj.add(v);
		
	}
	
	public void addObj(ObjectData obj ) {
		
		this.otherObjectsInCity.add(obj);
		
	}
	
	
	
	public ArrayList<ObjectData> getOtherObjectsInCity() {
		return otherObjectsInCity;
	}


	public void setOtherObjectsInCity(ArrayList<ObjectData> otherObjectsInCity) {
		this.otherObjectsInCity = otherObjectsInCity;
	}


	public ArrayList<Values> getDistanceToOtherObj() {
		return distanceToOtherObj;
	}

	public void setDistanceToOtherObj(ArrayList<Values> distanceToOtherObj) {
		this.distanceToOtherObj = distanceToOtherObj;
	}
	
	
	
	
	
	
	
	
	
}
