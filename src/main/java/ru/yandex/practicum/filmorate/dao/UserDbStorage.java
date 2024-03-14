package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

@RequiredArgsConstructor
@Repository
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User createUser(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        int id = insert.executeAndReturnKey(userToMap(user)).intValue();
        return user.setId(id);
    }

    private static Map<String, Object> userToMap(User user) {
        return Map.of(
                "email", user.getEmail(),
                "login", user.getName(),
                "name", user.getName(),
                "birthday", user.getBirthday()
        );
    }

//    @Override
//    public User updateUser(User user) {
//        return null;
//    }
//
//    @Override
//    public List<User> getAllUsers() {
//        return null;
//    }

    @Override
    public User getUserById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", getUserMapper(), id);
    }

    private static RowMapper<User> getUserMapper() {
        return (rs, rowNum) -> new User(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getDate("birthday").toLocalDate()
        );
    }

//    @Override
//    public void addFriend(Long id, Long friendId) {
//
//    }
//
//    @Override
//    public void deleteFriendById(Long id, Long friendId) {
//
//    }
//
//    @Override
//    public List<User> getAllFriends(Long id) {
//        return null;
//    }
//
//    @Override
//    public List<User> getFriendsCommon(Long id, Long otherId) throws ValidationException {
//        return null;
//    }
}
