package com.project.webservice.taskmanagerservice.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.project.webservice.taskmanagerservice.entity.Task;
import com.project.webservice.taskmanagerservice.entity.User;
import com.project.webservice.taskmanagerservice.service.EmailService;
import com.project.webservice.taskmanagerservice.service.TaskService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class SendingAutoEmailConfig {

	private final TaskService taskService;
	private final EmailService emailService;
	
	public SendingAutoEmailConfig (EmailService emailService, TaskService taskService ) {
		this.emailService = emailService;
		this.taskService = taskService;
	}
	
	@Scheduled(cron = "0 0 12 * * ?")
	public void sendEmailForTasksEndTwoDays() {
		List<Task> tasks = taskService.findAllByTaskDueDateBetween(LocalDate.now(), LocalDate.now().plusDays(2));	
		
		for (Task task : tasks) {
	         User user = task.getUser();
	         String emailContent = "Your task \"" + task.getTitle() + "\" is due in two days.";
	         emailService.sendEmail(user.getEmail(), "Task Due Reminder", emailContent);
	     }
		
	}
	
}
