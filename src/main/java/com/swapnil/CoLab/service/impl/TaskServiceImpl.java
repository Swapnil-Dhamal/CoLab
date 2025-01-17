package com.swapnil.CoLab.service.impl;

import com.swapnil.CoLab.dto.TaskDTO;
import com.swapnil.CoLab.exception.ResourceNotFoundException;
import com.swapnil.CoLab.exception.TaskAlreadyExistsException;
import com.swapnil.CoLab.mapper.TaskMapper;
import com.swapnil.CoLab.model.Project;
import com.swapnil.CoLab.model.Task;
import com.swapnil.CoLab.repository.TaskRepo;
import com.swapnil.CoLab.service.ProjectService;
import com.swapnil.CoLab.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {


    private final TaskRepo taskRepo;
    private final TaskMapper taskMapper;
    private final ProjectService projectService;


    @Transactional
    @Override
    public TaskDTO createTask(TaskDTO taskDTO)  {

        Task task=taskMapper.toEntity(taskDTO);

        try {
            Task existingTask = taskRepo.findByName(taskDTO.getName());
            if (existingTask != null) {
                throw new TaskAlreadyExistsException(task.getName() + " already present");
            }

        } catch (TaskAlreadyExistsException e) {
            System.out.println(e.getMessage());
            throw e;
        }


        if(taskDTO.getProjectId() != null){
            Project project=new Project();
            project.setProjectId(task.getTaskId());
            task.setProject(project);
        }
        taskRepo.save(task);

        return taskMapper.toDTO(task);
    }





    @Override
    public List<TaskDTO> getTasksByProjectId(Long projectId) {

        Project project=projectService.findById(projectId);
        if(project==null){
            throw new ResourceNotFoundException("Project does not exists");
        }

        List<Task> tasks=taskRepo.findByProject_ProjectId(projectId);

        return tasks.stream()
                .map(task -> taskMapper.toDTO(task))
                .collect(Collectors.toList());

    }

    @Override
    public List<TaskDTO> getTasksByUser(Long userId) {
        return List.of();
    }

    @Transactional
    @Override
    public TaskDTO assignTaskToMember(Long taskId, Long userId) {
        return null;
    }

    @Transactional
    @Override
    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) {
        return null;
    }

    @Transactional
    @Override
    public void deleteTask(Long taskId) {

    }
}
