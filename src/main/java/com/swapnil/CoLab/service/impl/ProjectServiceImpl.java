package com.swapnil.CoLab.service.impl;

import com.swapnil.CoLab.dto.ProjectDTO;
import com.swapnil.CoLab.mapper.ProjectMapper;
import com.swapnil.CoLab.model.Project;
import com.swapnil.CoLab.repository.ProjectRepo;
import com.swapnil.CoLab.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;
    private final ProjectRepo projectRepo;


    @Override
    public Project createProject(ProjectDTO projectDTO) throws Exception {

        Project project=projectMapper.toEntity(projectDTO);

        Project existingProject=projectRepo.findByName(projectDTO.getName());
        if(existingProject!=null){
            throw new Exception("Project with the name "+project.getName()+" already present");
        }

        Project newProject=new Project();
        newProject.setName(project.getName());
        newProject.setDescription(project.getDescription());
        newProject.setStatus(project.getStatus());
        newProject.setStartDate(project.getStartDate());
        newProject.setEndDate(project.getEndDate());

        return projectRepo.save(newProject);
    }

    @Override
    public Project findById(Long projectId) {

        Project project=projectRepo.findById(projectId)
                .orElseThrow(()-> new RuntimeException("Project with id "+projectId+" not found"));
        return project;
    }

    @Override
    public List<Project> findAll() {
        return List.of();
    }

    @Override
    public Project updateProject(Long projectId, ProjectDTO projectDTO) {
        return null;
    }

    @Override
    public Project updateProjectStatus(Long projectId, String status) {
        return null;
    }

    @Override
    public List<Project> getProjectsByStatus(String status) {
        return List.of();
    }

    @Override
    public void deleteProject(Long projectId) {

    }
}
