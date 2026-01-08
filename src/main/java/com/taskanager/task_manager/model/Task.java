package com.taskanager.task_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @MongoId
    private String id;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private boolean completed = false;
}