package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

//    User updateUser(User user);
//
//    List<User> getAllUsers();

    User getUserById(Integer id);
//
//    void addFriend(Integer id, Integer friendId);
//
//    void deleteFriendById(Integer id, Integer friendId);
//
//    List<User> getAllFriends(Integer id);
//
//    List<User> getFriendsCommon(Integer id, Integer otherId) throws ValidationException;
}
