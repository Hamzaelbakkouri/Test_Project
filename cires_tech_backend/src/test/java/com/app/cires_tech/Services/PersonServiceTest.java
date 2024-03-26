package com.app.cires_tech.Services;

import com.app.cires_tech.Model.DTO.Auth.RegisterRequestDto;
import com.app.cires_tech.Model.DTO.Person.Profile;
import com.app.cires_tech.Model.Entity.Person;
import com.app.cires_tech.Model.Enums.Role;
import com.app.cires_tech.Repository.PersonRepository;
import com.app.cires_tech.Service.Implementation.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    private PersonServiceImpl personService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        personService = new PersonServiceImpl(personRepository, new ModelMapper());
    }

    @Test
    public void testGenerateUsers() {
        int limit = 5;
        List<RegisterRequestDto> users = personService.generateUsers(limit);
        assertNotNull(users);
        assertEquals(limit, users.size());
    }

    @Test
    public void testGetCurrentUser() {
        Authentication authentication = new TestingAuthenticationToken("testUser", null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(personRepository.findByUsername("testUser")).thenReturn(Optional.of(new Person()));

        Profile currentUser = personService.getCurrentUser();
        assertNotNull(currentUser);
    }

    @Test
    public void testGetUserProfile_Admin() {
        Authentication authentication = new TestingAuthenticationToken("adminUser", null,
                Collections.singleton(() -> "ROLE_ADMIN"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(personRepository.findByUsername("testUser")).thenReturn(Optional.of(new Person()));

        Profile userProfile = personService.getUserProfile("testUser");
        assertNotNull(userProfile);
    }

    @Test
    public void testGetUserProfile_User() {
        Authentication authentication = new TestingAuthenticationToken("user", null,
                Collections.singleton(() -> "ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(personRepository.findByUsernameAndRole("testUser", Role.USER)).thenReturn(Optional.of(new Person()));

        Profile userProfile = personService.getUserProfile("testUser");
        assertNotNull(userProfile);
    }

    @Test
    public void testGetUserProfile_NoRole() {
        Authentication authentication = new TestingAuthenticationToken("testUser", null,
                Collections.singleton(() -> "ROLE_UNKNOWN"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThrows(RuntimeException.class, () -> personService.getUserProfile("testUser"));
    }
}