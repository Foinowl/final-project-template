package com.epam.rd.izh.dto;

import com.epam.rd.izh.annotations.DateMatches;
import com.epam.rd.izh.annotations.UniqueLogin;
import com.epam.rd.izh.entity.AuthorizedUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Component
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorizedUserDto {
    private Long id;
    @NotEmpty(message = "first name must be filled in")
    private String firstName;
    @NotEmpty(message = "middle name must be filled in")
    private String middleName;
    @NotEmpty(message = "last name must be filled in")
    private String lastName;
    @NotEmpty(message = "date birth must be filled in")
    @DateMatches
    private String dateBirth;
    @UniqueLogin
    @Size(min = 3, message = "login must be greater than 3")
    private String login;
    @Size(min = 8, message = "password must be greater than 8")
    private String password;
    @NotEmpty(message = "fill role")
    private String role;

    public AuthorizedUser toUser() {
        return AuthorizedUser.builder()
                .id(id)
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .dateBirth(LocalDate.parse(dateBirth))
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
                .dateBirth(user.getDateBirth().toString())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
