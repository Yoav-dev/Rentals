package ex.okapi.rent;

import java.io.Serializable;

public class BuildingPK implements Serializable {
	private static final long serialVersionUID = 8562183072425436695L;
	
	String building_name;
	String building_city;
	
	BuildingPK() {
		
	}
	
	BuildingPK(String name, String city) {
		this.building_name = name;
		this.building_city = city;
	}
	
	public String getBuilding_name() {
		return building_name;
	}

	public void setBuilding_name(String building_name) {
		this.building_name = building_name;
	}

	public String getBuilding_city() {
		return building_city;
	}

	public void setBuilding_city(String building_city) {
		this.building_city = building_city;
	}
}