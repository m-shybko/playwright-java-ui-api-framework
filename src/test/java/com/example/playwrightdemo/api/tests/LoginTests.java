package com.example.playwrightdemo.api.tests;

import com.example.playwrightdemo.api.clients.AuthClient;
import com.example.playwrightdemo.api.clients.UserClient;
import com.example.playwrightdemo.api.models.auth.LoginRequest;
import com.example.playwrightdemo.api.models.auth.LoginResponse;
import com.example.playwrightdemo.api.models.user.User;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Epic("API")
@Feature("Login")
public class LoginTests extends BaseApiTest {

    private AuthClient authClient;

    @BeforeEach
    void setUpClient() {
        authClient = new AuthClient(requestContext);
    }

    @Test
    @DisplayName("Login with correct credentials")
    public void correctLoginTest() throws IOException {
        User user = getValidUser();

        LoginRequest loginRequest = new LoginRequest(user.username(), user.password());
        APIResponse response = authClient.login(loginRequest);
        assertEquals(201, response.status());

        LoginResponse loginResponse = readResponse(response, LoginResponse.class);
        assertNotNull(loginResponse.token());
    }

    @Test
    @DisplayName("Login with incorrect credentials")
    public void incorrectLoginTest() {
        LoginRequest loginRequest = new LoginRequest("test", "test");

        APIResponse response = authClient.login(loginRequest);
        assertEquals(401, response.status());
        assertEquals("username or password is incorrect", response.text());
    }

    private User getValidUser() throws IOException {
        UserClient userClient = new UserClient(requestContext);
        APIResponse response = userClient.getAllUsers();
        User[] users = readResponse(response, User[].class);
        return users[0];
    }
}
