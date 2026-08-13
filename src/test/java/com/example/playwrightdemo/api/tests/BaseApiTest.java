package com.example.playwrightdemo.api.tests;

import com.example.playwrightdemo.config.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseApiTest {
    private Playwright playwright;
    protected APIRequestContext requestContext;
    protected static final ObjectMapper objectMapper = new ObjectMapper();
    protected static final String BASE_URL = Config.get(Config.API_BASE_URL_KEY);

    @BeforeAll
    void setUp() {
        playwright = Playwright.create();
    }

    @BeforeEach
    void beforeEach() {
        requestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(BASE_URL));
    }

    @BeforeEach
    void setSuiteName() {
        Allure.getLifecycle().updateTestCase(tr ->
                tr.getLabels().removeIf(l -> "suite".equals(l.getName())));
        Allure.suite(getClass().getSimpleName());
    }

    @AfterEach
    void afterEach() {
        requestContext.dispose();
    }

    @AfterAll
    void tearDown() {
        playwright.close();
    }

    protected <T> T readResponse(APIResponse response, Class<T> clazz) throws IOException {
        return objectMapper.readValue(response.body(), clazz);
    }
}
