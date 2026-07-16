package com.example.playwrightdemo.ui.pages;

import com.example.playwrightdemo.ui.pages.components.InventoryItem;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class CheckoutStepTwoPage {
    private final Page page;
    private final Locator subtotalLabel;
    private final Locator cancelButton;
    private final Locator inventoryItem;
    private final Locator finishButton;

    public static final String URL = "https://www.saucedemo.com/checkout-step-two.html";

    public CheckoutStepTwoPage(Page page) {
        this.page = page;
        subtotalLabel = page.locator("div[data-test='subtotal-label']");
        cancelButton = page.locator("button[data-test='cancel']");
        inventoryItem = page.locator("div[data-test='inventory-item']");
        finishButton = page.locator("button[data-test='finish']");
    }

    public Locator getSubtotalLabel() {
        return subtotalLabel;
    }

    public Double getSubtotalAmount() {
        String priceString = subtotalLabel.textContent();
        return Double.parseDouble(priceString.substring(priceString.indexOf('$') + 1));
    }

    @Step("Click Cancel button")
    public void clickCancelButton() {
        cancelButton.click();
    }

    @Step("Go to Checkout Complete page")
    public CheckoutCompletePage clickFinishButton() {
        finishButton.click();
        return new CheckoutCompletePage(page);
    }

    public InventoryItem getItem(int index) {
        return new InventoryItem(inventoryItem.nth(index));
    }
}
