package ex.okapi.rent;

import java.util.Collection;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@IdClass(value = BuildingPK.class)
public class Building {
	@Id
	private String building_name;
	
	@Id
	private String building_city;
	
	@Column
	private long sqft;
	
	@OneToMany
	@JsonBackReference
	private Collection<Lease> leases = new TreeSet<Lease>();
	
	@Transient
	private MainSystem mainSystem;
	
	public Building() {
		
	}

	public Building(String name, String city, long sqft, MainSystem mainSystem) {
		this.building_name = name;
		this.building_city = city;
		this.sqft = sqft;
		this.mainSystem = mainSystem;
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

	public long getSqft() {
		return sqft;
	}

	public void setSqft(long sqft) {
		this.sqft = sqft;
	}

	public Collection<Lease> getLeases() {
		return leases;
	}

	public void setLeases(Collection<Lease> leases) {
		this.leases = leases;
	}
	
	void addLease(Lease lease) {
		lease.setBuilding(this);
		leases.add(lease);
		mainSystem.saveLease(lease);
		mainSystem.saveBuilding(this);
	}
	
	Building setMainSystem(MainSystem mainSystem) {
		this.mainSystem = mainSystem;
		
		return this;
	}
}