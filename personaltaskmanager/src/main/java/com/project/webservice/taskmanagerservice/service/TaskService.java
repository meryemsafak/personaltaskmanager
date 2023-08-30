package com.project.webservice.taskmanagerservice.service;

import java.time.LocalDate;
import java.util.List;


import com.project.webservice.taskmanagerservice.dto.TaskDto;
import com.project.webservice.taskmanagerservice.entity.Task;

import jakarta.validation.Valid;

public interface TaskService {
	
	void saveTask(TaskDto userDto, String mail);

	Task findTaskByTitle(String title);

    List<TaskDto> findUserTasks(String user);

	List<TaskDto> findAllTasks();

	void deleteTask(long id);

	TaskDto findTask(long id);

	void save(@Valid TaskDto task);

	List<Task> findAllByTaskDueDateBetween(LocalDate now, LocalDate plusDays);
    

}
