package com.epam.rd.izh.repository;

import com.epam.rd.izh.entity.AuthorizedUser;
import com.epam.rd.izh.mapper.UserMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
    String sql = "SELECT * FROM i_user as u where u.login = ?";

    AuthorizedUser authorizedUser = jdbcTemplate.queryForObject(sql, new Object[]{login}, userMapper);

    return authorizedUser;
  }

  @Nullable
  public AuthorizedUser getUserById(@Nonnull Long id) {
    String sql = "SELECT * FROM i_user as u where u.user_id = ?";

    AuthorizedUser authorizedUser = jdbcTemplate.queryForObject(sql, new Object[]{id}, userMapper);

    return authorizedUser;
  }

  public long getUserIdByLogin(@Nonnull String login) {
    String query_getAuthorizedUserByLogin = "SELECT u.id from i_user as u where u.login = ?";

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

  public List<AuthorizedUser> getAllUsers() {
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
            "on us.role_id = role.role_id";
    return jdbcTemplate.query(sql, userMapper);
  }

  public boolean deleteUser(long idUser){
    String sql = "DELETE FROM i_user WHERE i_user.user_id=?";

    return jdbcTemplate.update(
            sql, idUser
    ) > 0;
  }
}
