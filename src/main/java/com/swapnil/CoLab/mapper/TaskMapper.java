package com.swapnil.CoLab.mapper;

import com.swapnil.CoLab.dto.TaskDTO;
import com.swapnil.CoLab.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskDTO taskDTO);

    TaskDTO toDTO(Task task);
}
