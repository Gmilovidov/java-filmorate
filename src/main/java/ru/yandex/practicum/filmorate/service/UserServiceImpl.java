package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;
    private final UserValidator userValidator = new UserValidator();

    @Override
    public User createUser(User user) {
//        if (userValidator.checkNameUser(user)) {
//            user.setName(user.getLogin());
//        }
//        checkerValidUser(user);
        return userStorage.createUser(user);
    }

//    @Override
//    public User updateUser(User user) {
//        if (userValidator.checkNameUser(user)) {
//            user.setName(user.getLogin());
//        }
//        checkerValidUser(user);
//        return userStorage.updateUser(user);
//    }
//
//    @Override
//    public List<User> getAllUsers() {
//        return userStorage.getAllUsers();
//    }

    @Override
    public User getUserById(Integer id) {
       return userStorage.getUserById(id);
    }

//    @Override
//    public void addFriend(Long id, Long friendId) {
//        userStorage.addFriend(id, friendId);
//    }
//
//    @Override
//    public void deleteFriendById(Long id, Long friendId) {
//        userStorage.deleteFriendById(id, friendId);
//    }
//
//    @Override
//    public List<User> getAllFriends(Long id) {
//        return userStorage.getAllFriends(id);
//    }
//
//    @Override
//    public List<User> getFriendsCommon(Long id, Long otherId) throws ValidationException {
//        return userStorage.getFriendsCommon(id, otherId);
//    }

    private void checkerValidUser(User user) {
        try {
            if (userValidator.checkMail(user)) {
                throw new ValidationException("электронная почта не может быть пустой и должна содержать символ @");
            }
            if (userValidator.checkLogin(user)) {
                throw new ValidationException("логин не может быть пустым и содержать пробелы");
            }
            if (userValidator.checkBirthday(user)) {
                throw new ValidationException("дата рождения не может быть в будущем");
            }
        } catch (ValidationException exception) {
            log.info(exception.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }
}
