package com.project.webservice.taskmanagerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.webservice.taskmanagerservice.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findUserByEmail(String email);


}
