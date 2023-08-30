package com.project.webservice.taskmanagerservice.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.webservice.taskmanagerservice.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{

	Task findTaskByTitle(String email);
	
	List<Task> findByUserEmail(String user);

	Optional<Task> findById(long id);

	List<Task> findAllByDueDateBetween(LocalDate start, LocalDate end);


}
