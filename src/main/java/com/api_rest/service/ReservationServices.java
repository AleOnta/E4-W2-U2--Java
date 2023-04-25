package com.api_rest.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.api_rest.model.E_WorkstationStatus;
import com.api_rest.model.Reservation;
import com.api_rest.model.User;
import com.api_rest.model.Workstation;
import com.api_rest.repository.JpaReservationRepository;
import com.api_rest.repository.JpaUserRepository;
import com.api_rest.repository.JpaWorkstationRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReservationServices {

	// properties
		
	@Autowired private JpaReservationRepository repoReservation;
	
	@Autowired private JpaUserRepository repoUser;
	
	@Autowired private JpaWorkstationRepository repoWorkstation;
	
	@Autowired private WorkstationServices workstationService;
	
	@Autowired @Qualifier("fakeReservation")
	private ObjectProvider<Reservation> fakeRes;	
	
	// internal method
	
	public void createFakeReservation() {
		Reservation res = fakeRes.getObject();		
		User owner = repoUser.getRandomUser();
		Workstation ws = repoWorkstation.getRandomWorkstation();
		res.setOwner(owner);
		res.setLocation(ws);
		boolean toGoUser = checkUserReservation(res);
		boolean toGoWorkstat = checkWorkstationAvailability(res);
		if (toGoUser && toGoWorkstat) {
			repoReservation.save(res);
			log.info("Reservation correctly persisted on DB");
		} else {
			log.warn("Unable create a new reservation:");
			if (!toGoUser && toGoWorkstat) log.warn("User already has a reservation for that day"); 
			else if (!toGoWorkstat && toGoUser) log.warn("Workstation already occupied in that day");
			else log.warn("User already has a reservation for that day and workstation is already occupied!");
		}
	}
	
	public boolean checkUserReservation(Reservation r) {
		List<Reservation> userReservations = repoReservation.findByOwner(r.getOwner());
		userReservations = userReservations.stream().filter(res -> res.getDate().isEqual(r.getDate())).collect(Collectors.toList());
		if (userReservations.size() > 0) {
			return sameOrNot(userReservations, r);
		} else {
			return true;
		}
	}
	
	public boolean checkWorkstationAvailability(Reservation r) {
		List<Reservation> workstationReservations = repoReservation.findByLocation(r.getLocation());
		workstationReservations = workstationReservations.stream().filter(res -> res.getDate().isEqual(r.getDate())).collect(Collectors.toList());
		if (workstationReservations.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean checkWorkstationAvailabilityUpdate(Reservation r) {
		List<Reservation> workstationReservations = repoReservation.findByLocation(r.getLocation());
		workstationReservations = workstationReservations.stream().filter(res -> res.getDate().isEqual(r.getDate())).collect(Collectors.toList());
		if (workstationReservations.size() > 0) {
			return sameOrNot(workstationReservations, r);
		} else {
			return true;
		}
	}
	
	public boolean sameOrNot(List<Reservation> resList, Reservation r) {
		if(resList.get(0).getId() == r.getId()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void updateWorkstationAvailability(Reservation r) {
		Workstation ws = r.getLocation();
		if (r.getDate().isEqual(LocalDate.now())) {
			ws.setStatus(E_WorkstationStatus.UNAVAILABLE);
			workstationService.updateWorkstation(ws);
		} else {
			ws.setStatus(E_WorkstationStatus.AVAILABLE);
			workstationService.updateWorkstation(ws);
		}
	}
	
	public void updateWorkstationAvailabilityOnDelete(Reservation r) {
		Workstation ws = r.getLocation();
		if (r.getDate().isEqual(LocalDate.now())) {
			ws.setStatus(E_WorkstationStatus.AVAILABLE);
			workstationService.updateWorkstation(ws);
		}
	}
	
	// Jpa methods
	
		public String persistReservation(Reservation r) {
			boolean toGoUser = checkUserReservation(r);
			boolean toGoWorkstat = checkWorkstationAvailability(r);
			if (toGoUser && toGoWorkstat) {
				repoReservation.save(r);
				updateWorkstationAvailability(r);
				return "Reservation correctly persisted on DB";
			} else {
				log.warn("Unable create a new reservation:");
				if (!toGoUser && toGoWorkstat) throw new EntityExistsException("User already has a reservation for that day"); 
				else if (!toGoWorkstat && toGoUser) throw new EntityExistsException("Workstation already occupied in that day");
				else throw new EntityExistsException("User already has a reservation for that day and workstation is already occupied!");
			}
		}
			
		public String updateReservation(Reservation r) {
			if (repoReservation.existsById(r.getId())) {
				boolean toGoUser = checkUserReservation(r);
				boolean toGoWorkstat = checkWorkstationAvailabilityUpdate(r);
				if (toGoUser && toGoWorkstat) {
					repoReservation.save(r);
					updateWorkstationAvailability(r);
					return "Reservation correctly updated on DB";
				} else {
					log.warn("Unable update reservation:");
					if (!toGoUser && toGoWorkstat) throw new EntityExistsException("User already has a reservation for that day"); 
					else if (!toGoWorkstat && toGoUser) throw new EntityExistsException("Workstation already occupied in that day");
					else throw new EntityExistsException("User already has a reservation for that day and workstation is already occupied!");
				}
			} else {
				throw new EntityNotFoundException("Reservation doesn't exists on Database");
			}
		}
			
		public String removeReservation(Reservation r) {
			if (repoReservation.existsById(r.getId())) {
				repoReservation.delete(r);
				updateWorkstationAvailabilityOnDelete(r);
				return "Reservation correctly removed from Database";
			} else {
				throw new EntityNotFoundException("Reservation doesn't exists on Database");
			}
		}
		
		public String removeReservation(Long id) {
			if (repoReservation.existsById(id)) {
				repoReservation.deleteById(id);
				return "Reservation correctly removed from Database";
			} else {
				throw new EntityNotFoundException("Reservation doesn't exists on Database");
			}
			
		}

		public Reservation findReservationById(Long id) {
			if (repoReservation.existsById(id)) {
				return repoReservation.findById(id).get();
			} else {
				throw new EntityNotFoundException("Reservation doesn't exists on Database");
			}
			
		}
		
		public List<Reservation> findAllReservation() {
			return (List<Reservation>) repoReservation.findAll();
		}
		
		public List<Reservation> findByUser(User u) {
			return repoReservation.findByOwner(u);
		}
	
}
