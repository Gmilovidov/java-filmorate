package ru.yandex.practicum.filmorate.exception;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateLikeException extends DuplicateKeyException {

    public DuplicateLikeException(String msg) {
        super(msg);
    }
}
