package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final List<User> users = new ArrayList<>();
    private Long generatedId = 0L;

    @Override
    public User createUser(User user) {
        user.setId(generateIdUser());
        users.add(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public User getUserById(Long id) {
        Optional <User> optionalUser = users.stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst();
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        Optional <User> optionalUser = users.stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst();
        Optional <User> optionalFriend = users.stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst();
        if (optionalUser.isEmpty() || optionalFriend.isEmpty() || id <=0 || friendId <=0) {
            throw new UserNotFoundException("Пользователь не найден");
        } else {
            getUserById(id).setFriendUser(friendId);
            getUserById(friendId).setFriendUser(id);
        }
    }

    @Override
    public void deleteFriendById(Long id, Long friendId) {
        Optional <User> optionalUser = users.stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst();
        Optional <User> optionalFriend = users.stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst();
        if (optionalUser.isEmpty() || optionalFriend.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        } else {
            getUserById(id).getFriends().remove(friendId);
        }
    }

    @Override
    public List<User> getAllFriends(Long id) {
        Optional <User> optionalUser = users.stream()
                .filter(user -> Objects.equals(user.getId(), id))
                .findFirst();
        if (optionalUser.isEmpty() || id <= 0) {
            throw new UserNotFoundException("Пользователь не найден");
        } else {
            User us = optionalUser.get();
            return users.stream()
                    .filter(user ->us.getFriends().contains(user.getId()))
                    .collect(Collectors.toList());
        }
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

    @Override
    public User updateUser(User user) {
        try {
            for (User u : users) {
                if (Objects.equals(u.getId(), user.getId())) {
                    break;
                } else {
                    throw new ValidationException("пользователь не найден");
                }
            }
            users.removeIf(u -> Objects.equals(u.getId(), user.getId()));
            users.add(user);
        } catch (ValidationException exception) {
            log.info(exception.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
        return user;
    }

    private Long generateIdUser() {
        return ++generatedId;
    }
}
