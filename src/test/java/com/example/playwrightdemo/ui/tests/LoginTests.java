package com.example.playwrightdemo.ui.tests;

import com.example.playwrightdemo.ui.data.TestUsers;
import com.example.playwrightdemo.ui.pages.InventoryPage;
import com.example.playwrightdemo.ui.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Epic("UI")
@Feature("Login")
public class LoginTests extends BaseTest {

    @Test
    @DisplayName("Successful login")
    public void successfulLoginTest() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.open();
        loginPage.login(TestUsers.STANDARD);
        assertThat(page).hasURL(InventoryPage.URL);

        InventoryPage inventoryPage = new InventoryPage(page);
        assertThat(inventoryPage.getInventoryList()).isVisible();

    }

    @ParameterizedTest
    @DisplayName("Login with incorrect credentials")
    @CsvSource({
            "standard_user, wrong_password",
            "wrong_user,    secret_sauce",
            "wrong_user,    wrong_password",
            "!\\\"№;%:?*(), !\\\"№;%:?*()"
    })
    public void incorrectLoginTest(String username, String password) {
        LoginPage loginPage = new LoginPage(page);
        loginPage.open();
        loginPage.login(username, password);
        assertThat(loginPage.getErrorMessage()).hasText("Epic sadface: Username and password do not match any user in this service");
    }

    @ParameterizedTest
    @DisplayName("Login with empty parameters")
    @CsvSource({
            "'',            secret_sauce, Username",
            "'',            '',           Username",
            "standard_user, '',           Password"
    })
    public void emptyRequiredFieldLoginTest(String username, String password, String errorField) {
        LoginPage loginPage = new LoginPage(page);
        loginPage.open();
        loginPage.login(username, password);
        assertThat(loginPage.getErrorMessage()).hasText("Epic sadface: %s is required".formatted(errorField));
    }

    @Test
    @DisplayName("Login as locked user")
    public void lockedUserLoginTest() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.open();
        loginPage.login(TestUsers.BLOCKED);
        assertThat(loginPage.getErrorMessage()).hasText("Epic sadface: Sorry, this user has been locked out.");
    }
}
