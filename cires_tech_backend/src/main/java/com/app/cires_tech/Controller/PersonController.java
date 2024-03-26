package com.app.cires_tech.Controller;

import com.app.cires_tech.Model.DTO.Auth.RegisterRequestDto;
import org.springframework.core.io.ByteArrayResource;
import com.app.cires_tech.Model.DTO.Person.Profile;
import com.app.cires_tech.Model.Enums.Role;
import org.springframework.core.io.Resource;
import com.app.cires_tech.Service.Implementation.AuthenticationService;
import com.app.cires_tech.Service.Interfaces.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class PersonController {
    private final PersonService personService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final AuthenticationService authenticationService;

    @GetMapping("/generate")
    public ResponseEntity<Resource> exportUsersToJson(@RequestParam Integer count) {
        try {
            List<RegisterRequestDto> users = personService.generateUsers(count);
            String jsonData = objectMapper.writeValueAsString(users);
            ByteArrayResource resource = new ByteArrayResource(jsonData.getBytes());
            String fileName = "users_" + LocalDateTime.now() + ".json";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(resource);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RejectedExecutionException("Downloading Employees JSON Failed, Due to : " + e.getMessage());
        }
    }

    @PostMapping("/batch")
    public Map<String, String> importProfiles(MultipartFile file) {
        try {
            String jsonContent = new String(file.getBytes());
            Map<String, String> handle = new HashMap<>();
            List<RegisterRequestDto> profileList = objectMapper.readValue(jsonContent, new TypeReference<List<RegisterRequestDto>>() {
            });
            for (RegisterRequestDto profile : profileList) {
                if (profile.getRole() == Role.USER) {
                    authenticationService.register(profile);
                } else if ((profile.getRole() == Role.ADMIN)) {
                    authenticationService.registerAdmin(profile);
                }
                handle.put("status", "All users Inserted Successfully");
            }
            return handle;
        } catch (Exception e) {
            log.error("error : {}", e.getMessage());
            throw new RuntimeException("Importing Profiles JSON Failed, Due to : " + e.getMessage());
        }
    }


    @GetMapping("/me")
    public Profile getCurrentUser() {
        return this.personService.getCurrentUser();
    }

    @GetMapping("/{username}")
    public Profile getByUserName(@PathVariable String username) {
        return this.personService.getUserProfile(username);
    }
}
