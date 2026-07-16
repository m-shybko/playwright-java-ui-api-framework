package com.example.playwrightdemo.api.clients;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Step;

public class UserClient extends BaseClient {

    public UserClient(APIRequestContext requestContext) {
        super(requestContext);
    }

    @Step("Get all clients")
    public APIResponse getAllUsers() {
        return get("/users");
    }

}
