package com.swapnil.CoLab.service.impl;

import com.swapnil.CoLab.domain.STATUS;
import com.swapnil.CoLab.dto.ProjectDTO;
import com.swapnil.CoLab.exception.ResourceNotFoundException;
import com.swapnil.CoLab.mapper.ProjectMapper;
import com.swapnil.CoLab.model.Project;
import com.swapnil.CoLab.model.Users;
import com.swapnil.CoLab.repository.ProjectRepo;
import com.swapnil.CoLab.repository.UserRepo;
import com.swapnil.CoLab.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;
    private final ProjectRepo projectRepo;
    private final UserRepo userRepo;


    @Override
    public Project createProject(ProjectDTO projectDTO) throws Exception {

        Optional<Users> manager=userRepo.findById(projectDTO.getManager_id());
        if(manager.isEmpty()){
            throw new RuntimeException("Manager not found");
        }

        Users manager1=manager.get();
        System.out.println("Manager name: "+manager1.getFullName());

        Project project=projectMapper.toEntity(projectDTO);

        Project existingProject=projectRepo.findByName(projectDTO.getName());
        if(existingProject!=null){
            throw new Exception("Project with the name "+project.getName()+" already present");
        }



        project.setManager(manager.get());
        System.out.println("Final manager name: "+project.getManager().getFullName());
        return projectRepo.save(project);
    }

    @Override
    public Project findById(Long projectId) {

        Project project=projectRepo.findById(projectId)
                .orElseThrow(()-> new RuntimeException("Project with id "+projectId+" not found"));
        return project;
    }

    @Override
    public Users findProjectManager(Long projectId) {

        Optional<Project> project=projectRepo.findById(projectId);
        if(project.isEmpty()){
            throw new ResourceNotFoundException("Project does not exist");
        }
        Project project1=project.get();
        Optional<Users> manager=userRepo.findById(project1.getManager().getUserId());
        return manager.get();
    }

    @Override
    public List<Project> findAll() {

        return projectRepo.findAll();
    }

    @Override
    public Project updateProject(Long projectId, ProjectDTO projectDTO) {

        Optional<Project> existingProject = projectRepo.findById(projectId);
        if(existingProject.isEmpty()){
            throw new RuntimeException("Project does not exist");
        }

        Project projectToUpdate = existingProject.get();

        Project updatedProject=projectMapper.toEntity(projectDTO);

        projectToUpdate.setName(updatedProject.getName());
        projectToUpdate.setDescription(updatedProject.getDescription());
        projectToUpdate.setStatus(updatedProject.getStatus());
        projectToUpdate.setStartDate(updatedProject.getStartDate());
        projectToUpdate.setEndDate(updatedProject.getEndDate());
        projectRepo.save(projectToUpdate);

        return projectToUpdate;
    }

    @Override
    public Project updateProjectStatus(Long projectId, String status) {

        Optional<Project> existingProject = projectRepo.findById(projectId);
        if(existingProject.isEmpty()){
            throw new RuntimeException("Project does not exist");
        }

        Project projectToUpdateStatus = existingProject.get();

        projectToUpdateStatus.setStatus(STATUS.valueOf(status));

        return projectRepo.save(projectToUpdateStatus);
    }

    @Override
    public List<Project> getProjectsByStatus(String status) {

        STATUS statusEnum = STATUS.valueOf(status.toUpperCase());

        return projectRepo.findAllByStatus(statusEnum);
    }

    @Override
    public void deleteProject(Long projectId) throws Exception {


        Optional<Project> existingProject=projectRepo.findById(projectId);

        Project project=existingProject.get();
        System.out.println(project.getName());
        if(existingProject.isEmpty()){
            throw new NoSuchElementException("Project does not exist");
        }
        projectRepo.deleteById(projectId);

    }
}
