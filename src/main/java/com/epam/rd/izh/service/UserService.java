package com.epam.rd.izh.service;

import com.epam.rd.izh.Model.PageImplBean;
import com.epam.rd.izh.dto.AuthorizedUserDto;
import com.epam.rd.izh.dto.UserDto;
import com.epam.rd.izh.entity.AuthorizedUser;
import com.epam.rd.izh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
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

    public boolean isLoginAvailable(String login) {

        AuthorizedUser user = getUserByLogin(login);

        return user == null;

    }

    public Long getUserIdByLogin(String login) {
        return userRepository.getUserIdByLogin(login);
    }

    @Nullable
    public AuthorizedUser getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    @Nullable
    public AuthorizedUserDto getAuthorizedUserDto(String login) {
        return AuthorizedUserDto.fromUser(userRepository.getUserByLogin(login));
    }

    public PageImplBean getAllUsers(String login, PageRequest paging)
            throws SecurityException{
        AuthorizedUserDto authorizedUserDto = getAuthorizedUserDto(login);

        if(!authorizedUserDto.getRole().equals("ADMIN")){
            throw new SecurityException();
        }

        return userRepository.getAllUsers(paging);
    }

    public boolean delete(@NotNull Long id) {
       return userRepository.deleteUser(id);
    }
}
