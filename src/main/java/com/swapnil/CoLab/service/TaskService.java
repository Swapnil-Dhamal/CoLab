package com.swapnil.CoLab.service;

import com.swapnil.CoLab.dto.TaskDTO;
import com.swapnil.CoLab.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    public TaskDTO createTask(TaskDTO taskDTO) throws Exception;

    public List<TaskDTO> getTasksByProjectId(Long projectId);

    public List<TaskDTO> getTasksByUser(Long userId);

    public TaskDTO assignTaskToMember(Long taskId, Long userId);

    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO);

    public void deleteTask(Long taskId);
}
