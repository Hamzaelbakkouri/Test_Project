package com.app.cires_tech.Model.DTO.Person;

import com.app.cires_tech.Model.Enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Profile implements Serializable {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String city;
    private String country;
    private String avatar;
    private String company;
    private String jobPosition;
    private String mobile;
    private String username;
    private String email;
    private Role role;
}
