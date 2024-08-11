package com.example.repository;

import com.example.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends CrudRepository<CommentEntity, String>, PagingAndSortingRepository<CommentEntity, String> {

    @Query("from CommentEntity ce where ce.taskId=?1")
    Page<CommentEntity> findByTaskId(String taskId, PageRequest pageable);
}
