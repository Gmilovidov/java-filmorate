package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import ru.yandex.practicum.filmorate.exception.DuplicateAddFriendException;
import ru.yandex.practicum.filmorate.exception.FriendshipNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@Qualifier("userDbStorage")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Long id = insert.executeAndReturnKey(user.userToMap(user)).longValue();
        user.setId(id);
        if (!user.getFriends().isEmpty()) {
            for (long idf : user.getFriends()) {
                jdbcTemplate.update("INSERT INTO friendship (id_users, id_friends) VALUES (?, ?)",
                id, idf);
            }
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        if (checkContainsUser(user.getId())) {
            String ms = "Пользователь с id = " + user.getId() + " не найден.";
            log.info(ms);
            throw new UserNotFoundException(ms);
        }

        String sqlQry = "UPDATE users SET " +
                "email = ?, " +
                "login = ?, " +
                "name = ?, " +
                "birthday = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sqlQry,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );
        jdbcTemplate.update("DELETE FROM friendship WHERE id_users = ?", user.getId());

        if (user.getFriends() != null && user.getFriends().size() != 0) {
            user.getFriends().forEach(id -> jdbcTemplate.update("INSERT INTO friendship (id_users, id_friends)" +
                    " VALUES (?, ?)", user.getId(), id));
        }
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM users", this::buildUser);
    }

    @Override
    public User getUserById(Long id) {
        if (checkContainsUser(id)) {
            String ms = "Пользователь с id = " + id + "не найден.";
            log.info(ms);
            throw new UserNotFoundException(ms);
        }
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", this::buildUser, id);
    }

    private User buildUser(ResultSet rs, int rowNum) throws SQLException {
        User user = User.builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .name(rs.getString("name"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
        user.getFriends().addAll(jdbcTemplate.query("SELECT id_friends FROM friendship WHERE id_users = ?",
                (rs1, rowNum1) -> rs1.getLong("id_friends"),
                user.getId())
        );
        return user;
    }

    @Override
    public User addFriend(Long id, Long friendId) {
        if (checkContainsUser(id)) {
            String ms = "Пользователь с id = " + id + " не найден.";
            log.info(ms);
            throw new UserNotFoundException(ms);
        }
        if (checkContainsUser(friendId)) {
            String ms = "Пользователь с id = " + friendId + " не найден.";
            log.info(ms);
            throw new UserNotFoundException(ms);
        }
        if (Objects.equals(id, friendId)) {
            log.info("попытка добавиться самому к себе в друзья");
            throw new ValidationException(" добавиться самому к себе в друзья запрещено");
        }
        String sql = "INSERT INTO friendship (id_users, id_friends) VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql, id, friendId);
        } catch (DuplicateKeyException e) {
            String ms = "Нельзя дважды добавляться в друзья";
            log.info(ms);
            throw new DuplicateAddFriendException(ms);
        }
        return getUserById(id);
    }

    @Override
    public void deleteFriendById(Long id, Long friendId) {
        if (checkContainsUser(id)) {
            String ms = "Пользователь с id = " + id + " не найден.";
            log.info(ms);
            throw new UserNotFoundException(ms);
        }
        if (checkContainsUser(friendId)) {
            String ms = "Пользователь с id = " + friendId + " не найден.";
            log.info(ms);
            throw new UserNotFoundException(ms);
        }
        if (jdbcTemplate.update("DELETE FROM friendship" +
                " WHERE id_users = ? AND id_friends = ?", id, friendId) == 0) {
            String ms = "Пользователи не состоят в дружбе";
            log.info(ms);
            throw new FriendshipNotFoundException(ms);
        }
    }

    @Override
    public List<User> getAllFriends(Long id) {
        if (checkContainsUser(id)) {
            String ms = "Пользователь с id = " + id + " не найден.";
            log.info(ms);
            throw new UserNotFoundException(ms);
        }
        String sql = "SELECT id_friends FROM friendship WHERE id_users = ?";
        List<Long> listFriendsId = jdbcTemplate.query(sql, (rs1, nowNum1) -> rs1.getLong("id_friends"), id);
        return listFriendsId.stream()
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getFriendsCommon(Long id, Long otherId) throws ValidationException {
        if (id <= 0 || otherId <= 0) {
            throw new ValidationException("id не могут быть отрицательными");
        } else {
            List<User> friends = getAllFriends(id);
            List<User> otherFriends = getAllFriends(otherId);
            friends.retainAll(otherFriends);
            return friends;
        }
    }

    private boolean checkContainsUser(Long userId) {
        try {
            jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", this::buildUser, userId);
            return false;
        } catch (EmptyResultDataAccessException exception) {
            return true;
        }
    }
}
