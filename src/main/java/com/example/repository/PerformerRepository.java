package com.example.repository;

import com.example.entity.PerformerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PerformerRepository extends CrudRepository<PerformerEntity, String>, PagingAndSortingRepository<PerformerEntity, String> {

    @Query("select p.performerId from PerformerEntity p where p.taskId=?1")
    List<String> findAllPerformerByTaskId(String id);
}
