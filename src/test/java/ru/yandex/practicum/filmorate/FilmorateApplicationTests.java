package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.FilmValidator;
import org.junit.jupiter.api.Assertions;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.time.LocalDate;

@SpringBootTest
class FilmorateApplicationTests {

	FilmValidator filmValidator;
	UserValidator userValidator;
	Film film;
	User user;
	Boolean valid;

	@BeforeEach
	void beforeEach() {
		filmValidator = new FilmValidator();
		userValidator = new UserValidator();
	}

	@Test
	void shouldReturnFalseForFilmValidations() {
		film = new Film("name", "descript", LocalDate.now().minusDays(1), 100);
		valid = filmValidator.checkName(film)
				&& filmValidator.checkLength(film)
				&& filmValidator.checkDuration(film)
				&& filmValidator.checkRelease(film);
		Assertions.assertFalse(valid, "не прошла общая валидация");
	}

	@Test
	void checkNameShouldReturnTrueForVoidName() {
		film = new Film("", "descript", LocalDate.now().minusDays(1), 100);
		valid = filmValidator.checkName(film);
		Assertions.assertTrue(valid, "не прошла проверка на пустое имя фильма");
	}

	@Test
	void checkLengthShouldReturnTrueForLengthDescription210() {
		film = new Film("name", "descriptttdescriptttdescriptttdescriptttdescriptttdes" +
				"criptttdescriptttdescriptttdescriptttdescriptttdescriptttdescriptttdescr" +
				"iptttdescriptttdescriptttdescriptttdescriptttdescriptttdescripttt" +
				"descriptttdescripttt", LocalDate.now().minusDays(1),100);
		valid = filmValidator.checkLength(film);
		Assertions.assertTrue(valid, "не прошла проверка на длину описания фильма");
	}

	@Test
	void checkReleaseShouldReturnTrueForEarlyReleaseValid() {
		film = new Film("name", "descript", LocalDate.now().minusYears(140), 100);
		valid = filmValidator.checkRelease(film);
		Assertions.assertTrue(valid, "не проходит проверка на рализ");
	}

	@Test
	void checkDurationShouldReturnTrueForDurationNegative1() {
		film = new Film("name", "descript", LocalDate.now().minusDays(1), -1);
		valid = filmValidator.checkDuration(film);
		Assertions.assertTrue(valid, "не проходит проверку на длительность");
	}

	@Test
	void shouldReturnFalseForUserValidations() {
		user = new User("mail@yandex.ru", "login", "name", LocalDate.now().minusYears(1));
		valid = userValidator.checkMail(user)
				&& userValidator.checkLogin(user)
				&& userValidator.checkBirthday(user)
				&& userValidator.checkNameUser(user);
		Assertions.assertFalse(valid, "не прошла общая валидация");
	}

	@Test
	void checkMailShouldReturnTrueForMailWithoutAT() {
		user = new User("mailyandex.ru", "login", "name", LocalDate.now().minusYears(1));
		valid = userValidator.checkMail(user);
		Assertions.assertTrue(valid, "не прошла проверка на отсутствие AT");
	}

	@Test
	void checkLoginShouldReturnTrueForLoginWithVoid() {
		user = new User("mail@yandex.ru", "log in", "name", LocalDate.now().minusYears(1));
		valid = userValidator.checkLogin(user);
		Assertions.assertTrue(valid, "не прошла проверка на пробел в логине");
	}

	@Test
	void checkBirthdayShouldReturnTrueForBDInFuture() {
		user = new User("mail@yandex.ru", "login", "name", LocalDate.now().plusYears(1));
		valid = userValidator.checkBirthday(user);
		Assertions.assertTrue(valid, "не прошла проверка на день рождения в будущем");
	}

	@Test
	void checkNameUserShouldReturnTrueForNull() {
		user = new User("mail@yandex.ru", "login", null, LocalDate.now().minusYears(1));
		valid = userValidator.checkNameUser(user);
		Assertions.assertTrue(valid, "не прошла проверка с именем null");
	}

	@Test
	void checkNameUserShouldReturnTrueForBlank() {
		user = new User("mail@yandex.ru", "login", "", LocalDate.now().minusYears(1));
		valid = userValidator.checkNameUser(user);
		Assertions.assertTrue(valid, "не прошла проверка с пустым именем");
	}
}
