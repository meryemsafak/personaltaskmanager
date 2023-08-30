package com.project.webservice.taskmanagerservice.service;

import com.project.webservice.taskmanagerservice.dto.TaskDto;
import com.project.webservice.taskmanagerservice.entity.Task;
import com.project.webservice.taskmanagerservice.entity.User;

import jakarta.validation.Valid;

import com.project.webservice.taskmanagerservice.dao.TaskRepository;
import com.project.webservice.taskmanagerservice.dao.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	public TaskServiceImpl(UserRepository userRepository, TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Task findTaskByTitle(String email) {
		return taskRepository.findTaskByTitle(email);
	}

	@Override
	public List<TaskDto> findUserTasks(String user) {
		List<Task> tasks = taskRepository.findByUserEmail(user);
		return tasks.stream().map((task) -> convertEntityToDto(task)).collect(Collectors.toList());
	}

	private TaskDto convertEntityToDto(Task task) {
		TaskDto taskDto = new TaskDto();
		taskDto.setTitle(task.getTitle());
		taskDto.setDescription(task.getDescription());
		taskDto.setDueDate(task.getDueDate());
		taskDto.setStatus(task.getStatus() == 1);
		taskDto.setId(task.getId());
		return taskDto;
	}

	public void saveTask(TaskDto taskDto, String loggedInUserEmail) {
		User user = userRepository.findUserByEmail(loggedInUserEmail); // Fetch the user by email
		Task task = new Task();
		task.setTitle(taskDto.getTitle());
		task.setDescription(taskDto.getDescription());
		task.setDueDate(taskDto.getDueDate());
		task.setUser(user);
		taskRepository.save(task);

	}

	@Override
	public List<TaskDto> findAllTasks() {
		List<Task> tasks = taskRepository.findAll();
		return tasks.stream().map((task) -> convertEntityToDto(task)).collect(Collectors.toList());
	}

	@Override
	public void deleteTask(long id) {
		Optional<Task> task = Optional.ofNullable(
				taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id)));
		taskRepository.delete(task.get());
	}

	@Override
	public TaskDto findTask(long id) {
		Optional<Task> task = Optional.ofNullable(
				taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id)));
		return convertEntityToDto(task.get());
	}

	@Override
	public void save(@Valid TaskDto taskDto) {
		Optional<Task> t = Optional.ofNullable(taskRepository.findById(taskDto.getId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + taskDto.getId())));
		Task task = t.get();
		task.setTitle(taskDto.getTitle());
		task.setDescription(taskDto.getDescription());
		task.setDueDate(taskDto.getDueDate());
		taskRepository.save(task);

	}

	@Override
	public List<Task> findAllByTaskDueDateBetween(LocalDate start, LocalDate end) {
		return taskRepository.findAllByDueDateBetween(start, end);
	}

}
