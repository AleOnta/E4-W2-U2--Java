package com.api_rest.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.api_rest.model.*;

@Repository
public interface JpaReservationRepository extends CrudRepository<Reservation, Long> {

	// finders 
	
	public List<Reservation> findByLocation(Workstation location);
	
	public List<Reservation> findByDate(LocalDate date);
	
	public List<Reservation> findByOwner(User owner);
	
	// exists
	
	public boolean existsById(Long id);
}
