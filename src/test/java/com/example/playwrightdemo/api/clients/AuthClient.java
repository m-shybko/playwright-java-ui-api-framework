package com.example.playwrightdemo.api.clients;

import com.example.playwrightdemo.api.models.auth.LoginRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Step;

public class AuthClient extends BaseClient {

    public AuthClient(APIRequestContext requestContext) {
        super(requestContext);
    }

    @Step("Login as {loginRequest}")
    public APIResponse login(LoginRequest loginRequest) {
        return post("/auth/login", loginRequest);
    }
}
