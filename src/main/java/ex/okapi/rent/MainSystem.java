package ex.okapi.rent;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class MainSystem {
	private static int MINIMUM_ANNUAL_RENT = 1300000;
	private static final DateFormat LEASE_DATES_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final Sort LEASES_SORT = Sort.by("Type");
	
	@Autowired
	private Buildings buildings;
	
	@Autowired
	private Leases leases;
		
	private Building findBuilding(BuildingPK buildingPK) {
		Optional<Building> building = buildings.findById(buildingPK);
		
		if (building == null || building == Optional.<Building>empty()) {
			return null;
		}
		
		return building.get().setMainSystem(this);
	}
	
	public void addLease(String buildingName,
						long buildingSqft,
						String buildingCity,
						String number,
						String type,
						String tenant,
						int unit,
						long unit_sqft,
						Date begin,
						Date end,
						double annual_rent_sqft,
						long annual_rent) {
		BuildingPK buildingPK = new BuildingPK(buildingName, buildingCity);
		Building building = findBuilding(buildingPK);
		
		if (building == null) {
			building = new Building(buildingName, buildingCity, buildingSqft, this);
			saveBuilding(building);
		}
				
		Optional<Lease> lease = leases.findById(new LeasePK(buildingPK, number));
		
		if (lease == null || lease == Optional.<Lease>empty()) {
			building.addLease(new Lease(number, type, tenant, unit, unit_sqft, begin, end, annual_rent_sqft, annual_rent));			
		}
	}
	
	@RequestMapping(value = "/add/lease", method = RequestMethod.POST, consumes = { MediaType.TEXT_PLAIN_VALUE })
	public void addLease(@RequestBody String body) throws NumberFormatException, ParseException, IOException {
		StringReader reader = new StringReader(body);

		CSVParser parser = CSVParser.parse(reader, CSVFormat.EXCEL.withHeader());
		List<CSVRecord> leases = parser.getRecords();
			
		for (CSVRecord record : leases) {
			long annualRent = Long.valueOf(record.get("Annual Rent"));
				
			if (annualRent <= MINIMUM_ANNUAL_RENT) {
				continue;
			}
				
			addLease(record.get("Property Name"),
				Long.valueOf(record.get("Property Sqft")),
				record.get("City"),
				record.get("Lease Number"),
				record.get("Lease Type"),
				record.get("Tenant Name"),
				Integer.valueOf(record.get("Unit Number")),
				Long.valueOf(record.get("Unit Sqft")),
				LEASE_DATES_FORMAT.parse(record.get("Lease Begin Date")),
				LEASE_DATES_FORMAT.parse(record.get("Lease End Date")),
				Double.valueOf(record.get("Annual Rent Sqft")),
				annualRent);
			}
	}
	
	@RequestMapping(value = "/retrieve/leases", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> retrieveLeases() {		
		return new ResponseEntity<Object>(leases.findAll(LEASES_SORT), HttpStatus.OK);
	}
	
	void saveLease(Lease lease) {
		leases.<Lease>saveAndFlush(lease);
	}
	
	void saveBuilding(Building building) {
		buildings.<Building>saveAndFlush(building);
	}
}