package com.swapnil.CoLab.mapper;

import com.swapnil.CoLab.dto.ManagerDTO;
import com.swapnil.CoLab.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ManagerMapper {


    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "email", target = "email")
    ManagerDTO toDTO(Users user);

    Users toEntity(ManagerDTO managerDTO);
}
