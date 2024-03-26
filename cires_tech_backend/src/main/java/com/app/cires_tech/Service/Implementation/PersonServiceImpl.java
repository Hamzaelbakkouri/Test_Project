package com.app.cires_tech.Service.Implementation;

import com.app.cires_tech.Model.DTO.Auth.RegisterRequestDto;
import com.app.cires_tech.Model.DTO.Person.Profile;
import com.app.cires_tech.Model.Enums.Role;
import com.app.cires_tech.Repository.PersonRepository;
import com.app.cires_tech.Service.Interfaces.PersonService;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<RegisterRequestDto> generateUsers(Integer limit) {
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService()
        );
        Random random = new Random();
        List<RegisterRequestDto> users = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            users.add(
                    RegisterRequestDto
                            .builder()
                            .firstName(faker.name().firstName())
                            .lastName(faker.name().lastName())
                            .birthday(faker.date().birthday())
                            .city(faker.address().city())
                            .country(faker.address().country())
                            .avatar(faker.avatar().image())
                            .company(faker.company().name())
                            .jobPosition(faker.job().position())
                            .mobile(fakeValuesService.bothify("0########"))
                            .username(fakeValuesService.bothify("????##"))
                            .email(fakeValuesService.bothify("????##@gmail.com"))
                            .password(fakeValuesService.bothify("##????##??"))
                            .role(random.nextBoolean() ? Role.USER : Role.ADMIN)
                            .build()
            );
        }
        return users;
    }

    @Override
    public Profile getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username != null) {
            return this.modelMapper.map(this.personRepository.findByUsername(username).get(), Profile.class);
        }
        throw new RuntimeException("UserName Not Found Try Login");
    }

    @Override
    public Profile getUserProfile(String userName) {
        String adminRole = "ROLE_" + Role.ADMIN;
        String userRole = "ROLE_" + Role.USER;

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authorities.stream().anyMatch(auth -> adminRole.equals(auth.getAuthority()))) {
            return this.modelMapper.map(this.personRepository.findByUsername(userName).get(), Profile.class);
        } else if (authorities.stream().anyMatch(auth -> userRole.equals(auth.getAuthority()))) {
            return this.modelMapper.map(this.personRepository.findByUsernameAndRole(userName, Role.USER).get(), Profile.class);
        } else {
            throw new RuntimeException("User has no specific role");
        }
    }


}
