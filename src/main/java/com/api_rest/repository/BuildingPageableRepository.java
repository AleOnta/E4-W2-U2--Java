package com.api_rest.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.api_rest.model.Building;

public interface BuildingPageableRepository extends PagingAndSortingRepository<Building, Long> {
	
	
	
}
