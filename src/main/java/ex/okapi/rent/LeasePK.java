package ex.okapi.rent;

import java.io.Serializable;

public class LeasePK implements Serializable {
	private static final long serialVersionUID = 8947875208429564135L;
	
	BuildingPK building;
	String number;
	
	LeasePK() {
		
	}
	
	LeasePK(BuildingPK building, String number) {
		this.building = building;
		this.number = number;
	}

	public BuildingPK getBuilding() {
		return building;
	}
	
	public void setBuilding(BuildingPK building) {
		this.building = building;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
}