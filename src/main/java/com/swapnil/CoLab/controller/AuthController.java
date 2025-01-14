package com.swapnil.CoLab.controller;


import com.swapnil.CoLab.config.JwtProvider;
import com.swapnil.CoLab.dto.LoginDTO;
import com.swapnil.CoLab.dto.UserRegistrationDTO;
import com.swapnil.CoLab.mapper.UserMapper;
import com.swapnil.CoLab.model.Users;
import com.swapnil.CoLab.repository.UserRepo;
import com.swapnil.CoLab.response.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserDetailsService userDetailsService;


    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> signUp(
            @Valid @RequestBody UserRegistrationDTO registrationDTO) throws Exception {

        Users user=userMapper.toEntity(registrationDTO);

        Optional<Users> existingEmail=userRepo.findByEmail(user.getEmail());

        if(existingEmail.isPresent()){
            throw new Exception("Email already exists");
        }

        System.out.println("DTO Password: " + registrationDTO.getPassword());

        System.out.println("User Password: "+user.getPassword());
        Users newUser=new Users();
        newUser.setEmail(user.getEmail());


        if (user.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        String encryptedPassword= passwordEncoder.encode(user.getPassword());
        System.out.println("encryptedPassword: "+encryptedPassword);
        newUser.setPassword(encryptedPassword);
        newUser.setFullName(user.getFullName());

        Users savedUser=userRepo.save(newUser);

        Authentication auth= new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(),
                savedUser.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt= JwtProvider.generatedToken(auth);
        System.out.println("Jwt: "+jwt);

        AuthResponse res=new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Register Success");

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> singIn(@Valid @RequestBody LoginDTO loginDTO){

        Users user = new Users();
        user.setEmail(loginDTO.getEmail());
        user.setPassword(loginDTO.getPassword());

        Authentication auth=authenticate(user.getEmail(), user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt=JwtProvider.generatedToken(auth);

        AuthResponse res=new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login successful");

        return new ResponseEntity<>(res, HttpStatus.OK);


    }


    private Authentication authenticate(String email, String password){

        UserDetails userDetails=userDetailsService.loadUserByUsername(email);

        if(userDetails==null)
        {
            throw new BadCredentialsException("Invalid email");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

    }
}
