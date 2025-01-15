package com.swapnil.CoLab.service;

import com.swapnil.CoLab.dto.ProjectDTO;
import com.swapnil.CoLab.model.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {

    public Project createProject(ProjectDTO projectDTO) throws Exception;

    public Project findById(Long projectId);

    public List<Project> findAll();

    public Project updateProject(Long projectId, ProjectDTO projectDTO);

    public Project updateProjectStatus(Long projectId, String status);

    public List<Project> getProjectsByStatus(String status);

    public void deleteProject(Long projectId);

}
