package com.swapnil.CoLab.mapper;

import com.swapnil.CoLab.dto.UserRegistrationDTO;
import com.swapnil.CoLab.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Configuration;


@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target="createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target="updatedAt", expression = "java(java.time.Instant.now())")
    Users toEntity(UserRegistrationDTO dto);

    UserRegistrationDTO toDTO(Users user);
}
