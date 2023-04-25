package com.api_rest.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.api_rest.model.User;

public interface UserPageableRepository extends PagingAndSortingRepository<User, Long> {

}
