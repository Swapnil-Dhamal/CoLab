package com.swapnil.CoLab.service.impl;

import com.swapnil.CoLab.config.JwtProvider;
import com.swapnil.CoLab.model.Users;
import com.swapnil.CoLab.repository.UserRepo;
import com.swapnil.CoLab.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;


    @Override
    public Optional<Users> findUserByEmail(String email) {

        Optional<Users> user=userRepo.findByEmail(email);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");

        }
        return user;
    }

    @Override
    public Users findUserProfileByJwt(String jwt) {

        String email=JwtProvider.getEmailFromToken(jwt);
        Optional<Users> user=userRepo.findByEmail(email);

        return user.orElseGet(
                () -> user.orElseThrow(
                        () -> new RuntimeException("User not found")));

    }

    @Override
    public Users findById(Long userId) {

        Optional<Users> user=userRepo.findById(userId);
        return user.orElseGet(
                ()-> user.orElseThrow(
                        ()-> new RuntimeException("User not found")));
    }
}

