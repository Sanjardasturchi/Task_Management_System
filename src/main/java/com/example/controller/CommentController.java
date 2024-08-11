package com.example.controller;

import com.example.dto.CommentDTO;
import com.example.dto.extra.CreateComment;
import com.example.enums.AppLanguage;
import com.example.service.CoomentService;
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
@Tag(name = "Comment Api list", description = "Api list for Comment")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CoomentService coomentService;

    @PostMapping("/create")
    @Operation(summary = "Api for create", description = "this api used for create comment")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> create(@Valid @RequestBody CreateComment commentDTO,
                                    @RequestHeader(value = "Accept-Language",defaultValue = "UZ") AppLanguage language) {
        return ResponseEntity.ok(coomentService.create(commentDTO,language));
    }

    @GetMapping("/get_by_taskId/{id}")
    @Operation(summary = "Api for get", description = "this api used for get task by owner id")
    public ResponseEntity<PageImpl<CommentDTO>> getByTaskId(@PathVariable("id")String id,
                                                      @RequestParam(value = "size") Integer size,
                                                      @RequestParam(value = "page") Integer page,
                                                      @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language){
        return ResponseEntity.ok(coomentService.getByTaskId(id,size,page,language));
    }

    @GetMapping("/getAll")
    @Operation(summary = "Api for get", description = "this api used for get all comments")
    public ResponseEntity<PageImpl<CommentDTO>> getAll(@RequestParam(value = "size") Integer size,
                                                       @RequestParam(value = "page") Integer page,
                                                       @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language){
        return ResponseEntity.ok(coomentService.getAll(size,page,language));
    }
}
