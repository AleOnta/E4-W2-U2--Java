package com.api_rest.service;

import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.api_rest.model.Building;
import com.api_rest.repository.BuildingPageableRepository;
import com.api_rest.repository.JpaBuildingRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BuildingServices {
	
	// properties
	
	@Autowired 
	private JpaBuildingRepository repoBuilding;
	
	@Autowired BuildingPageableRepository repoPageBuilding;
	
	@Autowired @Qualifier("fakeBuilding")
	private ObjectProvider<Building> fakeBuilding;
	
	// internal methods
	
	public void createfakeBuilding() {
		persistBuilding(fakeBuilding.getObject());
	}
	
	// Jpa methods
	
	public String persistBuilding(Building b) {
		if (!repoBuilding.existsByName(b.getName())) {
			repoBuilding.save(b);	
			return "Building correctly persisted on DB";
		} else {
			throw new EntityExistsException("Name: " + b.getName() + " already exists on Database");
		}
	}
	
	public String updateBuilding(Building b) {
		if (repoBuilding.existsById(b.getId())) {
			repoBuilding.save(b);
			return "Building correctly updated on DB";
		} else {
			throw new EntityNotFoundException("Building doesn't exists on Database");
		}
	}
	
	public String removeBuilding(Building b) {
		if (repoBuilding.existsById(b.getId())) {
			repoBuilding.delete(b);
			return "Building correctly removed from Database";
		} else {
			throw new EntityNotFoundException("Building doesn't exists on Database");
		}
	}
	
	public String removeBuilding(Long id) {
		if (repoBuilding.existsById(id)) {
			repoBuilding.deleteById(id);
			return "Building correctly removed from Database";
		} else {
			throw new EntityNotFoundException("Building doesn't exists on Database");
		}
	}
	
	public Building findById(Long id) {
		if (repoBuilding.existsById(id)) {
			return repoBuilding.findById(id).get();			
		} else {
			throw new EntityNotFoundException("Building doesn't exists on Database");
		}
	}
	
	public List<Building> findAllBuilding() {
		return (List<Building>) repoBuilding.findAll();
	}
	
	public Page<Building> findAllPageableBuilding(Pageable pageable) {
		return (Page<Building>) repoPageBuilding.findAll(pageable);
	}
}
