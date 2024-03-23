package com.app.cires_tech.Service.Implementation;

import com.app.cires_tech.Model.DTO.Person.Profile;
import com.app.cires_tech.Model.Entity.Person;
import com.app.cires_tech.Repository.PersonRepository;
import com.app.cires_tech.Service.Interfaces.PersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Profile> getAllUser(Integer limit) {
        List<Person> profiles = this.personRepository.findLimitedUsersOrderByCountryAsc(limit);
        return profiles.stream().map(person -> this.modelMapper.map(person, Profile.class)).collect(Collectors.toList());
    }
}
