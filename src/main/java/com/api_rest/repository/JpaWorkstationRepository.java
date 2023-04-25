package com.api_rest.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.api_rest.model.*;

@Repository
public interface JpaWorkstationRepository extends CrudRepository<Workstation, Long> {
	
	// finders
	public List<Workstation> findByStatus(E_WorkstationStatus status);
	
	public List<Workstation> findByType(E_WorkstationType type);
	
	// exists
	public boolean existsById(Long id);
	
	// custom queries
	
	@Query(value = "SELECT w FROM Workstation w INNER JOIN w.building b WHERE Lower(b.city) LIKE Lower('%' || :city || '%')")
	public List<Workstation> getByCity(String city);
	
	@Query(value = "SELECT w FROM Workstation w ORDER BY random() LIMIT 1")
	public Workstation getRandomWorkstation();
		
	@Query(value = "SELECT COUNT(w) FROM Workstation w")
	public Integer countHowManyWorkstation();
	
}
