package com.example.playwrightdemo.ui.tests;

import com.example.playwrightdemo.config.Config;
import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    private static Playwright playwright;
    private static Browser browser;
    protected static BrowserContext context;
    protected static Page page;

    private static final int slowmo = Config.getInt(Config.SLOWMO_KEY);
    private static final boolean headless = Config.getBoolean(Config.HEADLESS_KEY);

    @BeforeAll
    public static void beforeAll() {
        playwright = Playwright.create();

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(slowmo);

        browser = getBrowserType().launch(options);
    }

    @BeforeEach
    public void beforeEach() {
        context = browser.newContext();
        page = context.newPage();
    }

    @BeforeEach
    void setSuiteName() {
        Allure.getLifecycle().updateTestCase(tr ->
                tr.getLabels().removeIf(l -> "suite".equals(l.getName())));
        Allure.suite(getClass().getSimpleName()); // например "LoginTests" вместо com.example...LoginTests
    }

    @AfterEach
    public void afterEach() {
        context.close();
    }

    @AfterAll
    public static void afterAll() {
        playwright.close();
    }

    private static BrowserType getBrowserType() {
        return switch (Config.get(Config.BROWSER_KEY)) {
            case "firefox" -> playwright.firefox();
            case "webkit" -> playwright.webkit();
            default -> playwright.chromium();
        };
    }
}
