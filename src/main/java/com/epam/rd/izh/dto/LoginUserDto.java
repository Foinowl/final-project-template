package com.epam.rd.izh.dto;

import com.epam.rd.izh.entity.AuthorizedUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;

@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginUserDto {
    @Size(min = 3, message = "login must be greater than 3")
    private String login;
    @Size(min = 8, message = "password must be greater than 8")
    private String password;

    public static AuthorizedUserDto fromUser(AuthorizedUser user) {
        return AuthorizedUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .dateBirth(user.getDateBirth().toString())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
