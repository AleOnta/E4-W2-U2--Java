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
import com.api_rest.model.Workstation;
import com.api_rest.service.WorkstationServices;

@Controller
@RequestMapping("/api/workstations")
public class WorkstationController {
	
	@Autowired WorkstationServices worksService;
	
	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getWorkstation(@PathVariable Long id) {
		return new ResponseEntity<Workstation>(worksService.findWorkstationById(id), HttpStatus.FOUND);
	}
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getAllWorkstations() {
		return new ResponseEntity<List<Workstation>>(worksService.findAllWorkstations(), HttpStatus.FOUND);
	}
	
	@GetMapping("/pageable")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getAllWorkstationsPaged(Pageable pageable) {
		return new ResponseEntity<Page<Workstation>>(worksService.findAllWorkstations(pageable), HttpStatus.FOUND);
	}
	
	@GetMapping("/pageable/{city}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getAllWorkstationsPagedPerCity(@PathVariable String city, Pageable pageable) {
		return new ResponseEntity<Page<Workstation>>(worksService.findWorkstationsByCity(city, pageable), HttpStatus.FOUND);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> postNewWorkstation(@RequestBody Workstation works) {
		return new ResponseEntity<String>(worksService.persistWorkstation(works), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> putWorkstation(@RequestBody Workstation works) {
		return new ResponseEntity<String>(worksService.updateWorkstation(works), HttpStatus.OK);
	}
	
	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteWorkstation(@RequestBody Workstation works) {
		return new ResponseEntity<String>(worksService.removeWorkstation(works), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteWorkstationById(@PathVariable Long id) {
		return new ResponseEntity<String>(worksService.removeWorkstation(id), HttpStatus.OK);
	}
	
}
