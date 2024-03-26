package com.app.cires_tech.Service.Interfaces;

import com.app.cires_tech.Model.DTO.Auth.RegisterRequestDto;
import com.app.cires_tech.Model.DTO.Person.Profile;

import java.util.List;

public interface PersonService {
    List<RegisterRequestDto> generateUsers(Integer limit);
    Profile getCurrentUser();

    Profile getUserProfile(String username);
}
