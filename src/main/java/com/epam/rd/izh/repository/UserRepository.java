package com.epam.rd.izh.repository;

import com.epam.rd.izh.Model.PageImplBean;
import com.epam.rd.izh.dto.UserDto;
import com.epam.rd.izh.entity.AuthorizedUser;
import com.epam.rd.izh.mapper.UserMapper;
import com.epam.rd.izh.util.StringConstants;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
  private final List<AuthorizedUser> users = new ArrayList<>();

  private final JdbcTemplate jdbcTemplate;

  private final UserMapper userMapper;

  private final RoleRepository roleRepository;

  public UserRepository(JdbcTemplate jdbcTemplate, UserMapper userMapper, RoleRepository roleRepository) {
    this.jdbcTemplate = jdbcTemplate;
    this.userMapper = userMapper;
    this.roleRepository = roleRepository;
  }

  @Nullable
  public AuthorizedUser getUserByLogin(@Nonnull String login) {
    String sql = "SELECT * from i_user as u left join role on role.role_id = u.role_id where u.login = ?";

    try {
      AuthorizedUser authorizedUser = jdbcTemplate.queryForObject(sql, new Object[]{login}, userMapper);
      return authorizedUser;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Nullable
  public AuthorizedUser getUserById(@Nonnull Long id) {
    String sql = "SELECT * from i_user as u left join role on role.role_id = u.role_id where u.user_id = ?";

    AuthorizedUser authorizedUser = jdbcTemplate.queryForObject(sql, new Object[]{id}, userMapper);

    return authorizedUser;
  }

  public long getUserIdByLogin(@Nonnull String login) {
    String query_getAuthorizedUserByLogin = "SELECT u.id from i_user as u left join role on role.role_id = u.role_id where u.login = ?";

    return jdbcTemplate.queryForObject(query_getAuthorizedUserByLogin, new Object[]{login}, Long.class);
  }

  public boolean addUser(@Nullable AuthorizedUser user) {

    if (user != null) {

      Long roleId = roleRepository.getRoleIdByTitle(user.getRole());

      String sql = "insert into i_user " +
              "(first_name, middle_name, last_name, date_birth, login, passwords, role_id) " +
              "VALUES (?, ?, ?, ?, ?, ?, ?);";

      return jdbcTemplate.update(
              sql,
              user.getFirstName(),
              user.getMiddleName(),
              user.getLastName(),
              user.getDateBirth(),
              user.getLogin(),
              user.getPassword(),
              roleId
      ) > 0;
    }
    return false;
  }

  public PageImplBean<UserDto> getAllUsers(Pageable pageable) {
    String sql = "select " +
            "us.user_id, " +
            "first_name, " +
            "middle_name, " +
            "last_name , " +
            "date_birth, " +
            "login, " +
            "passwords, " +
            "role.title from " +
            "i_user as us " +
            "left join role " +
            "on us.role_id = role.role_id " +
            "order by us.user_id asc " +
//            "order by ? ? " +
            "limit ? offset ?";

//    String orderCol = pageable.getSort().getOrderFor(StringConstants.USER_SORT_BY_COL).getProperty();
//    String dir = pageable.getSort().getOrderFor(StringConstants.USER_SORT_BY_COL).getDirection().name();
    Integer limit = pageable.getPageNumber() * pageable.getPageSize();

    List<UserDto> list = jdbcTemplate.query(sql, new Object[]{pageable.getPageSize(), limit},userMapper).stream().map(UserDto::fromAuthorizedUser).collect(Collectors.toList());

    PageImplBean<UserDto> page = new PageImplBean<>(new PageImpl<>(list, pageable, count()));
    return page;
  }

  public boolean deleteUser(long idUser){
    String sql = "DELETE FROM i_user WHERE i_user.user_id=?";

    return jdbcTemplate.update(
            sql, idUser
    ) > 0;
  }

  private int count() {
    return jdbcTemplate.queryForObject("SELECT count(*) FROM i_user", Integer.class);
  }
}
