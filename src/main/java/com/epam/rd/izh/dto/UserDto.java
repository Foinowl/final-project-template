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
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String login;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateBirth;

    public static  UserDto fromAuthorizedUser(AuthorizedUser user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .dateBirth(user.getDateBirth())
                .build();
    }
}
