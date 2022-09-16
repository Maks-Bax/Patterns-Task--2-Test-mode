package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.testmode.data.DataGenerator;
import ru.netology.testmode.data.List;


import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.*;

public class AuthTest {

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = false;

    }

    @Test
    void shouldSuccessfulSubmission() {
        List Successful = DataGenerator.Registration.getUser("active");
        $("[data-test-id=login] input").setValue(Successful.getLogin());
        $("[data-test-id=password] input").setValue(Successful.getPassword());
        $("button[data-test-id=action-login]").click();
        $(withText("Личный кабинет")).shouldBe(appear);

    }

    @Test
    void shouldWithBlockedUser() {
        var blockedUser = DataGenerator.Registration.getUser("blocked");
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("button[data-test-id=action-login]").click();
        $(withText("Пользователь заблокирован")).shouldBe(appear);
    }

    @Test
    void shouldIncorrectUsername() {
        var registeredUser = shouldGetRegisteredUser("active");
        var IncorrectUsername = RandomLogin();
        $("[data-test-id=login] input").setValue(IncorrectUsername);
        $("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldIncorrectPassword() {
        var registeredUser = shouldGetRegisteredUser("active");
        var IncorrectPassword = RandomPassword();
        $("[data-test-id=login] input").setValue(registeredUser.getLogin());
        $("[data-test-id=password] input").setValue(IncorrectPassword);
        $(".button").shouldHave(Condition.text("Продолжить")).click();
        $("[data-test-id=\"error-notification\"]").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
}