package com.swapnil.CoLab.repository;

import com.swapnil.CoLab.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    public Task findByName(String name);

    public List<Task> findByProject_ProjectId(Long projectId);
}
