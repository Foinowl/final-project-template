package com.epam.rd.izh.dto;

import com.epam.rd.izh.entity.AuthorizedUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorizedUserDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateBirth;
    private String login;
    private String password;
    private String role;


    public AuthorizedUser toUser(){
        return AuthorizedUser.builder()
                .id(id)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .dateBirth(dateBirth)
                .login(login)
                .password(password)
                .role(role)
                .build();
    }

    public static AuthorizedUserDto fromUser(AuthorizedUser user) {
        return AuthorizedUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .dateBirth(user.getDateBirth())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
