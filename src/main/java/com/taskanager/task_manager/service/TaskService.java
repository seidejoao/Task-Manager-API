package com.taskanager.task_manager.service;

import com.taskanager.task_manager.model.Task;
import com.taskanager.task_manager.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task findById(String taskId){
        return taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task doesn't exist!"));
    }

    public Task addTask(Task task){
        try {
            if(task.getTitle() == null || task.getTitle().trim().isBlank()){
                throw new RuntimeException("Task title CANNOT be null/blank");
            } else{
                task.setTitle(task.getTitle().trim());
            }
            if(task.getDescription() != null && !task.getDescription().trim().isBlank()) {
                task.setDescription(task.getDescription().trim());
            }
            if(task.getStart() == null) {
                task.setStart(LocalDateTime.now());
            }
            if(task.getEnd() == null || task.getEnd().isBefore(LocalDateTime.now())){
                throw new RuntimeException("Task end date CANNOT be before now");
            }

            return taskRepository.save(task);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Task completeTask(String taskId){
        try {
            Task existingTask = findById(taskId);

            existingTask.setCompleted(true);

            return taskRepository.save(existingTask);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Task> getAllTasks(){
        try{
            return taskRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Task> getUncompletedTasks(){
        try {
            List<Task> uncompletedTasks = new ArrayList<>();

            for(Task task : taskRepository.findAll()){
                if(!task.isCompleted()){
                    uncompletedTasks.add(task);
                }
            }

            return uncompletedTasks;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Task> getCompletedTasks(){
        try {
            List<Task> completedTasks = new ArrayList<>();

            for(Task task : taskRepository.findAll()){
                if(task.isCompleted()){
                    completedTasks.add(task);
                }
            }

            return completedTasks;
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Task edit(Task task){
        try {
            Task existingTask = findById(task.getId());

            if(task.getTitle() != null && !task.getTitle().trim().isBlank()){
                existingTask.setTitle(task.getTitle().trim());
            }
            if(task.getDescription() != null && !task.getDescription().trim().isBlank()){
                existingTask.setDescription(task.getDescription().trim());
            }
            if(task.getEnd() != null && !task.getEnd().isAfter(LocalDateTime.now())){
                existingTask.setEnd(task.getEnd());
            }

            return taskRepository.save(existingTask);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteTaskById(String id){
        try {
            taskRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
