package com.api_rest.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.api_rest.service.ReservationServices;
import com.api_rest.service.UserServices;

@Component
public class ReservationsRunner implements ApplicationRunner {

	@Autowired UserServices userService;
	@Autowired ReservationServices resService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
	}

}
