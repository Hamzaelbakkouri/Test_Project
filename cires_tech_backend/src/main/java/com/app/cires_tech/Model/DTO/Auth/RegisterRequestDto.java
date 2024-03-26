package com.app.cires_tech.Model.DTO.Auth;

import com.app.cires_tech.Model.Enums.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {

    @Size(max = 30, message = "Last name is too long")
    @NotNull(message = "firstName name cannot be null")
    String firstName;

    @Size(max = 30, message = "Last name is too long")
    @NotNull(message = "userName name cannot be null")
    String lastName;

    @NotNull(message = "userName name cannot be null")
    @Size(max = 40, message = "username is too long")
    @Column(unique = true)
    String username;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email was not provided")
    @Size(max = 80, message = "Email is too long")
    @Column(unique = true)
    String email;

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    String password;

    @Past(message = "birthday must be in the past")
    @NotNull(message = "birthday cannot be null")
    Date birthday;

    @NotNull(message = "city cannot be null")
    @NotEmpty(message = "city cannot be empty")
    String city;

    @NotNull(message = "country cannot be null")
    @NotEmpty(message = "country cannot be empty")
    String country;

    @NotNull(message = "avatar cannot be null")
    @NotEmpty(message = "avatar cannot be empty")
    String avatar;

    @NotNull(message = "company cannot be null")
    @NotEmpty(message = "company cannot be empty")
    String company;

    @NotNull(message = "jobPosition cannot be null")
    @NotEmpty(message = "jobPosition cannot be empty")
    String jobPosition;

    @NotEmpty(message = "mobile cannot be empty")
    @Column(unique = true)
    String mobile;

    Role role;
}
