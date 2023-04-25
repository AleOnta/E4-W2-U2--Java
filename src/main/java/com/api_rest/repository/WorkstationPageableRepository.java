package com.api_rest.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.api_rest.model.Workstation;

public interface WorkstationPageableRepository extends PagingAndSortingRepository<Workstation, Long> {
	
	@Query(value = "SELECT w FROM Workstation w INNER JOIN w.building b WHERE Lower(b.city) LIKE Lower('%' || :city || '%')")
	public Page<Workstation> getByCity(String city, Pageable pageable);
	
}
