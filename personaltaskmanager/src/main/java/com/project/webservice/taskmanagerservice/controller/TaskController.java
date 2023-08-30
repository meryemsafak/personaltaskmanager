package com.project.webservice.taskmanagerservice.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.project.webservice.taskmanagerservice.service.TaskService;

import jakarta.validation.Valid;

import com.project.webservice.taskmanagerservice.dto.TaskDto;
import com.project.webservice.taskmanagerservice.entity.Task;

@Controller
public class TaskController {

	private TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping("/newtask")
	public String showNewTaskForm(Model model) {
		TaskDto task = new TaskDto();
		model.addAttribute("task", task);
		return "newtask";
	}

	private String getLoggedInUserName(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		return principal.toString();
	}

	@GetMapping("/tasks")
	public String listUserTasks(Model model) {
		String name = getLoggedInUserName(model);
		model.addAttribute("tasks", taskService.findUserTasks(name));
		return "tasks";
	}

	@GetMapping("/alltasks")
	public String listAllTasks(Model model) {
		model.addAttribute("tasks", taskService.findAllTasks());
		return "tasks";
	}

	@PostMapping("/task/save")
	public String registration(@Valid @ModelAttribute("task") TaskDto task, BindingResult result, Model model) {
		Task existing = taskService.findTaskByTitle(task.getTitle());
		if (existing != null) {
			result.rejectValue("title", null, "There is a task with same title: " + task.getTitle());
		}
		if (result.hasErrors()) {
			return "newtask";
		}
		String name = getLoggedInUserName(model);
		taskService.saveTask(task, name);
		return "redirect:/tasks?success";
	}

	@GetMapping("delete/{id}")
	public String deleteTask(@PathVariable("id") long id, Model model) {
		taskService.deleteTask(id);
		String name = getLoggedInUserName(model);
		model.addAttribute("tasks", taskService.findUserTasks(name));
		return "tasks";
	}

	@GetMapping("edit/{id}")
	public String editTask(@PathVariable("id") long id, Model model) {
		TaskDto task = taskService.findTask(id);
		model.addAttribute("task", task);
        return "updatetask";
    }

	@PostMapping("update/{id}")
	public String updateStudent(@PathVariable("id") long id, @Valid TaskDto task, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			task.setId(id);
			model.addAttribute("task", task);
			return "updatetask";
		}
		taskService.save(task);
		String name = getLoggedInUserName(model);
		model.addAttribute("tasks", taskService.findUserTasks(name));
		return "tasks";
	}

}
