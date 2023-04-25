package com.api_rest.service;

import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.api_rest.model.User;
import com.api_rest.repository.JpaUserRepository;
import com.api_rest.repository.UserPageableRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServices {

	@Autowired private JpaUserRepository repoUser;
	
	@Autowired private UserPageableRepository repoPageableUser;
	
	@Autowired @Qualifier("fakeUser")
	private ObjectProvider<User> fakeUser;
	
	// internal methods

	public String createfakeUser() {
		return persistUser(fakeUser.getObject());
	}
	
	// Jpa methods
	
	public String persistUser(User u) {
		if (!repoUser.existsByEmail(u.getEmail())) {
			repoUser.save(u);
			return "User correctly persisted on DB";
		} else {
			throw new EntityExistsException("Email: " + u.getEmail() + " already exists on Database");
		}
		
		
	}
	
	public String updateUser(User u) {
		if (repoUser.existsById(u.getId())) {
			repoUser.save(u);
			return "User correctly updated on DB";
		} else {
			throw new EntityNotFoundException("User doesn't exists on Database");
		}
		
	}
	
	public String removeUser(User u) {
		if (repoUser.existsById(u.getId())) {
			repoUser.delete(u);
			return "User correctly removed from Database";
		} else {
			throw new EntityNotFoundException("User doesn't exists on Database");
		}
	}
	
	public String removeUser(Long id) {
		if (repoUser.existsById(id)) {
			repoUser.deleteById(id);
			return "User correctly removed from Database";
		} else {
			throw new EntityNotFoundException("User doesn't exists on Database");
		}
	}
	
	public User findUserById(Long id) {
		if (repoUser.existsById(id)) {
			return repoUser.findById(id).get();
		} else {
			throw new EntityNotFoundException("User doesn't exists on Database");
		}
	}
	
	public List<User> findAllUser() {
		return (List<User>) repoUser.findAll();
	}
	
	public Page<User> findAllUserPageable(Pageable pageable) {
		return (Page<User>) repoPageableUser.findAll(pageable);
	}
	
	
}
