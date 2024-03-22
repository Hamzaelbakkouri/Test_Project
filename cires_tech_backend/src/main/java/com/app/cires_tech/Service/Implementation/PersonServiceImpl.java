package com.app.cires_tech.Service.Implementation;

import com.app.cires_tech.Model.DTO.Person.Profile;
import com.app.cires_tech.Repository.PersonRepository;
import com.app.cires_tech.Service.Interfaces.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Override
    public List<Profile> getAllUser(Integer limit) {
        return null;
    }
}
