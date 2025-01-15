package com.swapnil.CoLab.repository;

import com.swapnil.CoLab.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {

    public Project findByName(String projectName);
}
