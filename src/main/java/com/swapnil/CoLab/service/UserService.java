package com.swapnil.CoLab.service;

import com.swapnil.CoLab.dto.UserRegistrationDTO;
import com.swapnil.CoLab.model.Users;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<Users> findUserByEmail(String email);


    public Users findUserProfileByJwt(String jwt);

    public Users findById(Long userId);
}
