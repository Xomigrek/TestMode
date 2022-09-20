package ru.netology.testmode.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class


AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id=\"login\"] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id=\"password\"] .input__control").setValue(registeredUser.getPassword());
        $(".button").click();
        $(".heading").should(text("Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        Configuration.holdBrowserOpen = true;
        var notRegisteredUser = getUser("active");
        $("[data-test-id=\"login\"] .input__control").setValue(notRegisteredUser.getLogin());
        $("[data-test-id=\"password\"] .input__control").setValue(notRegisteredUser.getPassword());
        $(".button").click();
        $("[data-test-id=\"error-notification\"] .notification__content").should(text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        Configuration.holdBrowserOpen = true;
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id=\"login\"] .input__control").setValue(blockedUser.getLogin());
        $("[data-test-id=\"password\"] .input__control").setValue(blockedUser.getPassword());
        $(".button").click();
        $("[data-test-id=\"error-notification\"] .notification__content").should(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id=\"login\"] .input__control").setValue(wrongLogin);
        $("[data-test-id=\"password\"] .input__control").setValue(registeredUser.getPassword());
        $(".button").click();
        $("[data-test-id=\"error-notification\"] .notification__content").should(text("Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        Configuration.holdBrowserOpen = true;
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id=\"login\"] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id=\"password\"] .input__control").setValue(wrongPassword);
        $(".button").click();
        $("[data-test-id=\"error-notification\"] .notification__content").should(text("Неверно указан логин или пароль"));
    }
}
