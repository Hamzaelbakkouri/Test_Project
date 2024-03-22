package com.app.cires_tech.Service.Implementation;

import champ.fish.Aftas.Exeptions.NotFoundExeption;
import com.app.cires_tech.Model.DTO.Auth.AuthenticationRequestDto;
import com.app.cires_tech.Model.DTO.Auth.AuthenticationResponseDto;
import com.app.cires_tech.Model.DTO.Auth.RegisterRequestDto;
import com.app.cires_tech.Model.DTO.Person.Profile;
import com.app.cires_tech.Model.Entity.Person;
import com.app.cires_tech.Model.Entity.Token;
import com.app.cires_tech.Model.Enums.Role;
import com.app.cires_tech.Model.Enums.TokenType;
import com.app.cires_tech.Repository.PersonRepository;
import com.app.cires_tech.Repository.TokenRepository;
import com.app.cires_tech.Security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final PersonRepository personRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public AuthenticationResponseDto register(RegisterRequestDto request) {
        return createUser(request, Role.USER);
    }

    public AuthenticationResponseDto registerAdmin(RegisterRequestDto request) {
        return this.createUser(request, Role.ADMIN);
    }


    private AuthenticationResponseDto createUser(RegisterRequestDto request, Role role) {
        log.info("Creating a new user with role: {}", role);

        Person user = new Person();
        user.setFirstName(request.getFirstName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setLastName(request.getLastName());
        user.setCity(request.getCity());
        user.setCompany(request.getCompany());
        user.setAvatar(request.getAvatar());
        user.setBirthday(request.getBirthday());
        user.setUsername(request.getUsername());
        user.setCountry(request.getCountry());
        user.setRole(role);

        var savedUser = personRepository.save(user);
        log.info("User with ID {} created successfully.", savedUser.getId());

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);
        log.info("Access and refresh tokens generated and saved for user with ID: {}", savedUser.getId());

        return AuthenticationResponseDto.builder().accessToken(jwtToken).build();
    }


    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            log.error("Authentication failed for user: {}", request.getEmail(), ex);
            throw new BadCredentialsException("Invalid credentials");
        } catch (AuthenticationException ex) {
            log.error("Authentication failed for user: {}", request.getEmail(), ex);
            throw new ResourceNotFoundException("Authentication failed");
        }
        var user = personRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .build();
    }


    private void saveUserToken(Person user, String jwtToken) {
        var token = Token.builder().person(user).token(jwtToken).tokenType(TokenType.BEARER).expired(false).revoked(false).build();
        tokenRepository.save(token);
    }


    private void revokeAllUserTokens(Person user) {
        var validUserTokens = tokenRepository.findAllValidTokenByMember(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }


    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUserName(refreshToken);
        if (userEmail != null) {
            var user = this.personRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponseDto.builder().accessToken(accessToken).build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }


    public Boolean checkToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return false;
        }
        String jwt = token.substring(7);
        var userEmail = jwtService.extractUserName(jwt);
        if (userEmail != null) {
            var user = this.personRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            return jwtService.isTokenValid(jwt, user);
        }
        return false;
    }

    public Profile getUser(String name) {
        Person user = personRepository.findByEmail(name).orElseThrow(() -> new NotFoundExeption("User not found"));
        return modelMapper.map(user, Profile.class);
    }
}
