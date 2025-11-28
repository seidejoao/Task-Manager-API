package com.taskanager.task_manager.service;

import com.taskanager.task_manager.model.Task;
import com.taskanager.task_manager.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task addTask(Task task){
        try{
            if(task.getTitle() == null || task.getTitle().trim().isBlank()){
                throw new RuntimeException("Task title CANNOT be null/blank");
            } else{
                task.setTitle(task.getTitle().trim());
            }
            if(task.getDescription() != null && !task.getDescription().trim().isBlank()) {
                task.setDescription(task.getDescription().trim());
            }
            if(task.getStart() == null) {
                task.setStart(LocalDate.now());
            }
            if(task.getEnd() == null || task.getEnd().isBefore(LocalDate.now())){
                throw new RuntimeException("Task end date CANNOT be before now");
            }

            return taskRepository.save(task);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Task completeTask(Task task){
        try {
            Task existingTask = taskRepository.findById(task.getId()).orElseThrow(() -> new RuntimeException("Task doesn't exist!"));

            existingTask.setCompleted(true);

            return existingTask;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Task edit(String id, Task task){
        try {
            Task existingTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task doesn't exist!"));

            if(task.getTitle() != null && task.getTitle().trim().isBlank()){
                existingTask.setTitle(task.getTitle());
            }
            if(task.getDescription() != null && task.getDescription().trim().isBlank()){
                existingTask.setDescription(task.getDescription());
            }
            if(task.getEnd() != null && task.getEnd().isAfter(LocalDate.now())){
                existingTask.setEnd(task.getEnd());
            }

            return taskRepository.save(existingTask);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteById(String id){
        try {
            taskRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
