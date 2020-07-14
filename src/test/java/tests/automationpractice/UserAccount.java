package tests.automationpractice;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.parameter;
import static io.qameta.allure.Allure.step;

@Feature("UserAccount")
@Tag("auth")
public class UserAccount {
    String UrlAShop = "http://automationpractice.com/";
    String EmailAShop = "cherrycat+33@mail.ru";
    String PasswordAShop = "Polyris";
    String NameAShop = "Lena";
    String SurnameAShop = "Truelena";

    @Test
    @Story("Authentication of an existing user")
    @DisplayName("Successful login with a valid username and a valid password")
    void successfulLoginExistingUser () {
        parameter("url:", UrlAShop);
        parameter("Email:", EmailAShop);
        parameter("Password:", PasswordAShop);

        step("Open main page", ()-> open(UrlAShop));
        step("Click 'Sign in'", ()-> $(".header_user_info").click());
        step("Fill login form", ()-> {
            $("#login_form input[id=email]").setValue(EmailAShop);
            $("#login_form input[id=passwd]").setValue(PasswordAShop).pressEnter();
        });
        step("Assert that login ist successful", ()->
            $("body").shouldHave(text(NameAShop), text(SurnameAShop)));
    }
}
