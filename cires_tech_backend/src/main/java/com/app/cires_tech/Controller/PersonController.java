package com.app.cires_tech.Controller;

import com.app.cires_tech.Model.DTO.Person.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import com.app.cires_tech.Service.Interfaces.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class PersonController {
    private final PersonService personService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @GetMapping("/generate/{count}")
    public ResponseEntity<Resource> exportUsersToJson(@PathVariable Integer count) {
        try {
            List<Profile> users = personService.getAllUser(count);
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

}
