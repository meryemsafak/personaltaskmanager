package com.project.webservice.taskmanagerservice.service;

import java.util.List;

import com.project.webservice.taskmanagerservice.dto.UserDto;
import com.project.webservice.taskmanagerservice.entity.User;

public interface UserService {
	
	void saveUser(UserDto userDto);

	User findUserByEmail(String email);

    List<UserDto> findAllUsers();

}
