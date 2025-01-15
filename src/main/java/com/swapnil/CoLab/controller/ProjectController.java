package com.swapnil.CoLab.controller;

import com.swapnil.CoLab.domain.USER_ROLE;
import com.swapnil.CoLab.dto.ProjectDTO;
import com.swapnil.CoLab.mapper.ProjectMapper;
import com.swapnil.CoLab.model.Project;
import com.swapnil.CoLab.model.Users;
import com.swapnil.CoLab.service.ProjectService;
import com.swapnil.CoLab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final ProjectMapper projectMapper;

    @PostMapping("/create")
    public ResponseEntity<ProjectDTO> createProject(
            @RequestBody ProjectDTO projectDTO,
            @RequestHeader("Authorization") String jwt) throws Exception {

        Users user=userService.findUserProfileByJwt(jwt);

        if(user.getRole()== USER_ROLE.MEMBER){
            throw new RuntimeException("User should be a Manager to create the project");
        }

        Project project = projectService.createProject(projectDTO);
        ProjectDTO responseDTO = projectMapper.toDTO(project);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);



    }

    @GetMapping("/getProject/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(
            @PathVariable Long projectId){

        Project project = projectService.findById(projectId);
        ProjectDTO responseDTO = projectMapper.toDTO(project);

        return new ResponseEntity<>(responseDTO, HttpStatus.FOUND);

    }
}
