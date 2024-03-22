package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface UserStorage {

     User createUser(User user);

     void updateUser(User user);

     List<User> getAllUsers();

     User getUserById(Long id);

     User addFriend(Long id, Long friendId);

     void deleteFriendById(Long id, Long friendId);

     List<User> getAllFriends(Long id);

     List<User> getFriendsCommon(Long id, Long otherId) throws ValidationException;
}
