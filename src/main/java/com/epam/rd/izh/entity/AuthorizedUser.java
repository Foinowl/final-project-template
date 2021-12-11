package com.epam.rd.izh.entity;

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
public class AuthorizedUser {
  private Long id;
  private String firstName;
  private String middleName;
  private String lastName;
  private LocalDate dateBirth;
  private String login;
  private String password;
  private String role;
}