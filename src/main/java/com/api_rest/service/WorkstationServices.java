package com.api_rest.service;

import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.api_rest.model.Building;
import com.api_rest.model.E_WorkstationType;
import com.api_rest.model.Workstation;
import com.api_rest.repository.JpaBuildingRepository;
import com.api_rest.repository.JpaWorkstationRepository;
import com.api_rest.repository.WorkstationPageableRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
public class WorkstationServices {
	
	// properties
	
	@Autowired 
	private JpaWorkstationRepository repoWorkstation;
	
	@Autowired WorkstationPageableRepository repoPageWorkstation;
	
	@Autowired 
	private JpaBuildingRepository repoBuilding;
	
	@Autowired @Qualifier("fakeWorkstation")
	private ObjectProvider<Workstation> fakeWs;
		
	// internal method
	
	public void createFakeWorkstation() {
		Workstation ws = fakeWs.getObject();
		Building b = repoBuilding.getRandomBuilding();
		ws.setBuilding(b);
		persistWorkstation(ws);
	}
	
	// Jpa methods
		
	public String persistWorkstation(Workstation w) {
		repoWorkstation.save(w);
		return "Workstation correctly persisted on Database";
	}
		
	public String updateWorkstation(Workstation w) {
		if (repoWorkstation.existsById(w.getId())) {
			repoWorkstation.save(w);
			return "Workstation correctly updated on Database";
		} else {
			throw new EntityNotFoundException("Workstation doesn't exists on Database");
		}
	}
		
	public String removeWorkstation(Workstation w) {
		if (repoWorkstation.existsById(w.getId())) {
			repoWorkstation.delete(w);
			return "Workstation correctly deleted from Database";
		} else {
			throw new EntityNotFoundException("Workstation doesn't exists on Database");
		}
	}
	
	public String removeWorkstation(Long id) {
		if (repoWorkstation.existsById(id)) {
			repoWorkstation.deleteById(id);
			return "Workstation correctly deleted from Database";
		} else {
			throw new EntityNotFoundException("Workstation doesn't exists on Database");
		}
	}

	public Workstation findWorkstationById(Long id) {
		if (repoWorkstation.existsById(id)) {
			return repoWorkstation.findById(id).get();
		} else {
			throw new EntityNotFoundException("Workstation doesn't exists on Database");
		}
	}
	
	public List<Workstation> findAllWorkstations() {
		return (List<Workstation>) repoWorkstation.findAll();
	}
	
	public Page<Workstation> findAllWorkstations(Pageable pageable) {
		return (Page<Workstation>) repoPageWorkstation.findAll(pageable);
	}

	public List<Workstation> findWorkstationsByCity(String city) {
		return (List<Workstation>) repoWorkstation.getByCity(city);
	}
	
	// ON WORK
	public Page<Workstation> findWorkstationsByCity(String city, Pageable pageable) {
		return (Page<Workstation>) repoPageWorkstation.getByCity(city, pageable);
	}
	
	public List<Workstation> findWorkstationsByType(E_WorkstationType type) {
		return (List<Workstation>) repoWorkstation.findByType(type);
	}
	
}
