package com.example.repository;

import com.example.entity.TaskEntity;
import com.example.enums.Priority;
import com.example.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskRepository extends CrudRepository<TaskEntity, String>, PagingAndSortingRepository<TaskEntity, String> {

    @Transactional
    @Modifying
    @Query("Update TaskEntity  set title =?1 where id = ?2")
    void updateTitleById(String title, String taskId);

    @Transactional
    @Modifying
    @Query("Update TaskEntity  set taskStatus =?1 where id = ?2")
    void updateStatusById(Status taskStatus, String taskId);

    @Transactional
    @Modifying
    @Query("Update TaskEntity  set taskPriority =?1 where id = ?2")
    void updatePriorityById(Priority taskPriority, String taskId);

    @Query("from TaskEntity where ownerId = ?1")
    Iterable<TaskEntity> findAllByOwnerId(String id);

    @Query("from TaskEntity ce where ce.ownerId=?1")
    Page<TaskEntity> findByOwnerId(String id, PageRequest pageable);
}
