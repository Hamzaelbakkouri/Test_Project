package com.app.cires_tech.Controller;

import com.app.cires_tech.Model.DTO.Auth.AuthenticationRequestDto;
import com.app.cires_tech.Model.DTO.Auth.AuthenticationResponseDto;
import com.app.cires_tech.Model.DTO.Auth.RegisterRequestDto;
import com.app.cires_tech.Service.Implementation.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthenticationController {
    private final AuthenticationService service;


    @PostMapping
    public ResponseEntity<AuthenticationResponseDto> authenticate(@Valid @RequestBody final AuthenticationRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new ResourceNotFoundException(bindingResult.toString());
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@Valid @RequestBody final RegisterRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new ResourceNotFoundException(bindingResult.toString());
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<AuthenticationResponseDto> registerManager(@Valid @RequestBody final RegisterRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new ResourceNotFoundException(bindingResult.toString());

        return ResponseEntity.ok(service.registerAdmin(request));
    }
}
