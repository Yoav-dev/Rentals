package ex.okapi.rent;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@IdClass(value = LeasePK.class)
public class Lease implements Comparable<Lease>{
	@ManyToOne
	@Id
	@JsonManagedReference
	private Building building;
	
	@Id
	private String number;
	
	@Column
	private String type;
	
	@Column
	private String tenant;
	
	@Column
	private int unit;
	
	@Column
	private long unit_sqft;
	
	@Column
	private Date begin;
	
	@Column
	private Date end;
	
	@Column
	private double annual_rent_sqft;
	
	@Column
	private long annual_rent;
	
	public Lease() {
		
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public long getUnit_sqft() {
		return unit_sqft;
	}

	public void setUnit_sqft(long unit_sqft) {
		this.unit_sqft = unit_sqft;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public double getAnnual_rent_sqft() {
		return annual_rent_sqft;
	}

	public void setAnnual_rent_sqft(double annual_rent_sqft) {
		this.annual_rent_sqft = annual_rent_sqft;
	}

	public long getAnnual_rent() {
		return annual_rent;
	}

	public void setAnnual_rent(long annual_rent) {
		this.annual_rent = annual_rent;
	}

	public Lease(String number, String type, String tenant, int unit, long unit_sqft, Date begin,
			Date end, double annual_rent_sqft, long annual_rent) {
		this.number = number;
		this.type = type;
		this.tenant = tenant;
		this.unit = unit;
		this.unit_sqft = unit_sqft;
		this.begin = begin;
		this.end = end;
		this.annual_rent_sqft = annual_rent_sqft;
		this.annual_rent = annual_rent;
	}

	@Override
	public int compareTo(Lease lease) {
		int comparison = building.getBuilding_name().compareTo(lease.building.getBuilding_name());
		
		if (comparison != 0) {
			return comparison;
		}
		
		comparison = building.getBuilding_city().compareTo(lease.building.getBuilding_city());
		
		if (comparison != 0) {
			return comparison;
		}
		
		comparison = number.compareTo(lease.number);
		
		if (comparison != 0) {
			return comparison;
		}
		
		return 0;
	}
}