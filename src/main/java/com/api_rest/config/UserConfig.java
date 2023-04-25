package com.api_rest.config;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import com.github.javafaker.Faker;
import com.api_rest.model.User;

@Configuration
public class UserConfig {

	@Bean("customUser")
	@Scope("prototype")
	public User customUser() {
		return new User();
	}
	
	@Bean("fakeUser")
	@Scope("prototype")
	public User fakeUser() {
		Faker fake = Faker.instance(new Locale("it-IT"));
		String name = fake.name().firstName();
		String lastname = fake.name().lastName();
		String email = name + "." + lastname + "@example.com";
		String username = name + "_" + lastname;
		User u = User.builder().name(name).lastname(lastname).email(email).username(username).build();
		return u;
	}

	@Bean("paramsUser")
	@Scope("prototype")
	public User paramsUser(String name, String lastname) {
		String email =  name + "." + lastname + "@example.com";
		String username = name + "_" + lastname;
		return User.builder().name(name).lastname(lastname).email(email).username(username).build();
	}
}
