package com.example.playwrightdemo.ui.pages;

import com.example.playwrightdemo.ui.data.TestUser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class LoginPage {

    private final Page page;
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator errorMessage;

    public static final String URL = "https://www.saucedemo.com/";

    public LoginPage(Page page) {
        this.page = page;
        usernameInput = page.locator("input[data-test='username']");
        passwordInput = page.locator("input[data-test='password']");
        loginButton = page.locator("input[data-test='login-button']");
        errorMessage = page.locator("h3[data-test='error']");
    }

    public void open() {
        page.navigate(URL);
    }

    @Step("Login with {username}/{password}")
    public void login(String username, String password) {
        usernameInput.fill(username);
        passwordInput.fill(password);
        loginButton.click();
    }

    public void login(TestUser testUser) {
        login(testUser.username(), testUser.password());
    }

    public Locator getErrorMessage() {
        return errorMessage;
    }
}
