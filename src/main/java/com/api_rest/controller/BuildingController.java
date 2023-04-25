package com.api_rest.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.api_rest.model.Building;
import com.api_rest.model.User;
import com.api_rest.service.BuildingServices;


@Controller
@RequestMapping("/api/buildings")
public class BuildingController {

	@Autowired BuildingServices buildingService;
	
	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getBuilding(@PathVariable Long id) {
		return new ResponseEntity<Building>(buildingService.findById(id), HttpStatus.FOUND);
	}
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getAllBuildings() {
		return new ResponseEntity<List<Building>>(buildingService.findAllBuilding(), HttpStatus.FOUND);
	}
	
	@GetMapping("/pageable")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Page<Building>> getAllBuildingsPaged(Pageable pageable) {
		return new ResponseEntity<Page<Building>>(buildingService.findAllPageableBuilding(pageable), HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> postNewBuilding(@RequestBody Building building) {
		return new ResponseEntity<String>(buildingService.persistBuilding(building), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> putBuilding(@RequestBody Building building) {
		return new ResponseEntity<String>(buildingService.updateBuilding(building), HttpStatus.OK);
	}
	
	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteBuilding(@RequestBody Building building) {
		return new ResponseEntity<String>(buildingService.removeBuilding(building), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteBuildingById(@PathVariable Long id) {
		return new ResponseEntity<String>(buildingService.removeBuilding(id), HttpStatus.OK);
	}
}
