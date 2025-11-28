package com.taskanager.task_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @MongoId
    private String id;
    private String title;
    private String description;
    private LocalDate start;
    private LocalDate end;
    private boolean completed = false;
}