package ex.okapi.rent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Leases extends JpaRepository<Lease, LeasePK> {
}