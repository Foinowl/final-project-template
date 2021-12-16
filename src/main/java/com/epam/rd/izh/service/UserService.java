package com.epam.rd.izh.service;

import com.epam.rd.izh.dto.AuthorizedUserDto;
import com.epam.rd.izh.entity.AuthorizedUser;
import com.epam.rd.izh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public AuthorizedUser getAuthorizedUser(AuthorizedUserDto authorizedUserDto){

        authorizedUserDto.setPassword(passwordEncoder.encode(authorizedUserDto.getPassword()));

        return authorizedUserDto.toUser();
    }

    public boolean addAuthorizedUser(AuthorizedUser authorizedUser) {
        return userRepository.addUser(authorizedUser);
    }

    public long getAuthorizedUserId(String login){
        return userRepository.getUserIdByLogin(login);
    }

    public boolean isLoginAvailable(String login) {

        AuthorizedUser user = getUserByLogin(login);

        return user == null;

    }

    @Nullable
    public AuthorizedUser getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    @Nullable
    public AuthorizedUserDto getAuthorizedUserDto(String login) {
        return AuthorizedUserDto.fromUser(userRepository.getUserByLogin(login));
    }

    public List<AuthorizedUserDto> getAllAuthorizedUsers(String login)
            throws SecurityException{
        AuthorizedUserDto authorizedUserDto = getAuthorizedUserDto(login);

        if(!authorizedUserDto.getRole().equals("ADMIN")){
            throw new SecurityException();
        }

        return userRepository
                .getAllUsers()
                .stream()
                .map(AuthorizedUserDto::fromUser)
                .collect(Collectors.toList());
    }
}
