package com.example.controller;

import com.example.dto.TaskDTO;
import com.example.dto.extra.CreateTask;
import com.example.dto.extra.UpdateTask;
import com.example.dto.extra.UpdateTaskPriority;
import com.example.dto.extra.UpdateTaskStatus;
import com.example.enums.AppLanguage;
import com.example.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "Task Api list", description = "Api list for Task")
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    @Operation(summary = "Api for create", description = "this api used for create task")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> create(@Valid @RequestBody CreateTask taskDTO,
                                    @RequestHeader(value = "Accept-Language",defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(taskService.create(taskDTO,language));
    }

    @PutMapping("/update")
    @Operation(summary = "Api for update", description = "this api used for update task title")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateTask taskDTO,
                                    @RequestHeader(value = "Accept-Language",defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(taskService.update(taskDTO,language));
    }

    @PutMapping("/update_status")
    @Operation(summary = "Api for update", description = "this api used for update task status")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateStatus(@Valid @RequestBody UpdateTaskStatus taskDTO,
                                    @RequestHeader(value = "Accept-Language",defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(taskService.updateStatus(taskDTO,language));
    }

    @PutMapping("/update_priority")
    @Operation(summary = "Api for update", description = "this api used for update task priority")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updatePriority(@Valid @RequestBody UpdateTaskPriority taskDTO,
                                    @RequestHeader(value = "Accept-Language",defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(taskService.updatePriority(taskDTO,language));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Api for update", description = "this api used for update task priority")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> delete(@PathVariable("id")String id,
                                            @RequestHeader(value = "Accept-Language",defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(taskService.delete(id,language));
    }

    @GetMapping("/getAll")
    @Operation(summary = "Api for get", description = "this api used for get all task")
    public ResponseEntity<PageImpl<TaskDTO>> getAll(@RequestParam(value = "size") Integer size,
                                                @RequestParam(value = "page") Integer page,
                                                @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language){
        return ResponseEntity.ok(taskService.getAll(size,page,language));
    }

    @GetMapping("/getByOwnerId/{id}")
    @Operation(summary = "Api for get", description = "this api used for get task by owner id")
    public ResponseEntity<PageImpl<TaskDTO>> getByOwnerId(@PathVariable("id")String id,
                                                          @RequestParam(value = "size") Integer size,
                                                          @RequestParam(value = "page") Integer page,
                                                          @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language){
        return ResponseEntity.ok(taskService.getByOwnerId(id,size,page,language));
    }
}
