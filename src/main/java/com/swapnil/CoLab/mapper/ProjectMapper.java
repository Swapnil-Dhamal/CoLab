package com.swapnil.CoLab.mapper;


import com.swapnil.CoLab.dto.ProjectDTO;
import com.swapnil.CoLab.model.Project;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    Project toEntity(ProjectDTO projectDTO);

    ProjectDTO toDTO(Project project);
}
