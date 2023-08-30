package com.project.webservice.taskmanagerservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.webservice.taskmanagerservice.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {

	Role findByName(String string);

}
