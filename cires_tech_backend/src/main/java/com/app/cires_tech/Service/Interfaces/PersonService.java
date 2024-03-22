package com.app.cires_tech.Service.Interfaces;

import com.app.cires_tech.Model.DTO.Person.Profile;

import java.util.List;

public interface PersonService {
    List<Profile> getAllUser(Integer limit);
}
