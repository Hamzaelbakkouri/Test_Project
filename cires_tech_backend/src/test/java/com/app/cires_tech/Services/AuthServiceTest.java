package com.app.cires_tech.Services;

import com.app.cires_tech.Model.DTO.Auth.AuthenticationRequestDto;
import com.app.cires_tech.Model.DTO.Auth.AuthenticationResponseDto;
import com.app.cires_tech.Model.DTO.Auth.RegisterRequestDto;
import com.app.cires_tech.Model.Entity.Person;
import org.springframework.security.authentication.BadCredentialsException;
import com.app.cires_tech.Model.Enums.Role;
import com.app.cires_tech.Repository.PersonRepository;
import com.app.cires_tech.Repository.TokenRepository;
import com.app.cires_tech.Security.JwtService;
import com.app.cires_tech.Service.Implementation.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setUsername("testUser");
        registerRequestDto.setPassword("password");
        registerRequestDto.setRole(Role.USER);

        when(passwordEncoder.encode(registerRequestDto.getPassword())).thenReturn("encodedPassword");
        when(personRepository.save(any(Person.class))).thenReturn(new Person());
        when(jwtService.generateToken(any(Person.class))).thenReturn("accessToken");

        AuthenticationResponseDto responseDto = authenticationService.register(registerRequestDto);

        assertNotNull(responseDto);
        assertNotNull(responseDto.getAccessToken());
    }

    @Test
    void testAuthenticate_Success() {
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();
        authenticationRequestDto.setUsername("testUser");
        authenticationRequestDto.setPassword("password");

        Person user = new Person();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");

        when(personRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(authenticationRequestDto.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(any(Person.class))).thenReturn("accessToken");

        AuthenticationResponseDto responseDto = authenticationService.authenticate(authenticationRequestDto);

        assertNotNull(responseDto);
        assertNotNull(responseDto.getAccessToken());
    }

//    @Test
//    void testAuthenticate_Failure_InvalidCredentials() {
//        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();
//        authenticationRequestDto.setUsername("testUser");
//        authenticationRequestDto.setPassword("password");
//
//        Person user = new Person();
//        user.setUsername("testUser");
//        user.setPassword("encodedPassword");
//
//        when(personRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
//
//        when(passwordEncoder.matches(authenticationRequestDto.getPassword(), user.getPassword())).thenReturn(false);
//
//        assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(authenticationRequestDto));
//    }

    @Test
    void testAuthenticate_Failure_UserNotFound() {
        AuthenticationRequestDto authenticationRequestDto = new AuthenticationRequestDto();
        authenticationRequestDto.setUsername("nonExistingUser");
        authenticationRequestDto.setPassword("password");

        when(personRepository.findByUsername("nonExistingUser")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(authenticationRequestDto));
    }
}
