package com.swapnil.CoLab.controller;

import com.swapnil.CoLab.domain.USER_ROLE;
import com.swapnil.CoLab.dto.TaskDTO;
import com.swapnil.CoLab.exception.UnauthorizedAccessException;
import com.swapnil.CoLab.model.Task;
import com.swapnil.CoLab.model.Users;
import com.swapnil.CoLab.service.TaskService;
import com.swapnil.CoLab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {

    private final UserService userService;
    private final TaskService taskService;
    private final Logger log= LoggerFactory.getLogger(TaskController.class);


    @PostMapping("/create/{projectId}")
    public ResponseEntity<TaskDTO> createTask(
            @RequestHeader("Authorization") String jwt,
            @RequestBody TaskDTO taskDTO,
            @PathVariable Long projectId
    ) throws Exception {

        try {
            Users user = userService.findUserProfileByJwt(jwt);

            if (user.getRole() != USER_ROLE.MANAGER) {
                throw new UnauthorizedAccessException("Only Manager has access to create the task");
            }

            TaskDTO dto=taskService.createTask(taskDTO, projectId);
            log.info("Project id: {}", dto.getProjectId());
            return new ResponseEntity<>(dto,  HttpStatus.CREATED);

        }catch (UnauthorizedAccessException e){
            Task errorTask=new Task();
            TaskDTO errorTaskTTO=new TaskDTO(errorTask);
            errorTaskTTO.setName(e.getMessage());
            return new ResponseEntity<>(errorTaskTTO, HttpStatus.FORBIDDEN);
        }
    }


    @GetMapping("/tasks/{projectId}")
    public ResponseEntity<List<TaskDTO>> getTasksByProjectId(
            @PathVariable Long projectId
    ){

        return new ResponseEntity<>(
                taskService.getTasksByProjectId(projectId), HttpStatus.FOUND);

    }


    @PostMapping("/assignTask")
    public ResponseEntity<String> assignTaskToUser(
            @RequestHeader("Authorization") String jwt,
            @RequestParam Long taskId,
            @RequestParam Long userId
    ) throws Exception {
        Users user=userService.findUserProfileByJwt(jwt);

        if(user.getRole()!=USER_ROLE.MANAGER){
            log.error("Unauthorized attempt by user {} to assign task", user.getFullName());
            throw new UnauthorizedAccessException("Only Manager can assign task to team member");
        }
        taskService.assignTaskToMember(taskId, userId);

        return ResponseEntity.ok("Task assigned successfully");


    }

    @GetMapping("/getTasks/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByUserId(
            @PathVariable Long userId
    ){

        return new ResponseEntity<>(taskService.getTasksByUser(userId), HttpStatus.FOUND);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long taskId,
            @RequestBody TaskDTO taskDTO

    ){

        Users user=userService.findUserProfileByJwt(jwt);

        if(user.getRole()!=USER_ROLE.MANAGER){
            log.error("Unauthorized attempt by user {} to update the task", user.getFullName());
            throw new UnauthorizedAccessException("Only Manager can update the task");
        }


        return new ResponseEntity<>(taskService.updateTask(taskId, taskDTO), HttpStatus.OK);

    }


    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<String> deleteTask(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long taskId
    )
    {
        Users user=userService.findUserProfileByJwt(jwt);

        if(user.getRole()!=USER_ROLE.MANAGER){
            log.error("Unauthorized attempt by user {} to update the task", user.getFullName());
            throw new UnauthorizedAccessException("Only Manager can update the task");
        }

        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Task deleted");
    }
}
