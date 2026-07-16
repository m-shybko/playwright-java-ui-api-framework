package com.example.playwrightdemo.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class CheckoutCompletePage {
    private final Page page;
    private final Locator mainText;
    private final Locator homeButton;
    private final Locator cartIconBadge;

    public static final String URL = "https://www.saucedemo.com/checkout-complete.html";

    public CheckoutCompletePage(Page page) {
        this.page = page;
        mainText = page.locator("h2[data-test='complete-header']");
        homeButton = page.locator("button[data-test='back-to-products']");
        cartIconBadge = page.locator("span[data-test='shopping-cart-badge']");
    }

    public Locator getMainText() {
        return mainText;
    }

    public Locator cartIconBadge() {
        return cartIconBadge;
    }

    @Step("Click Home button")
    public void clickHomeButton() {
        homeButton.click();
    }
}
