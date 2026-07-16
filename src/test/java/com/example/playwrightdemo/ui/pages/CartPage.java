package com.example.playwrightdemo.ui.pages;

import com.example.playwrightdemo.ui.pages.components.InventoryItem;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class CartPage {
    private final Page page;
    private final Locator cartList;
    private final Locator inventoryItem;
    private final Locator continueButton;
    private final Locator checkoutButton;

    public static final String URL = "https://www.saucedemo.com/cart.html";

    public CartPage(Page page) {
        this.page = page;
        cartList = page.locator("div[data-test='cart-list']");
        inventoryItem = page.locator("div[data-test='inventory-item']");
        continueButton = page.locator("button[data-test='continue-shopping']");
        checkoutButton = page.locator("button[data-test='checkout']");
    }

    public Locator getCartList() {
        return cartList;
    }

    public InventoryItem getItem(int index) {
        return new InventoryItem(inventoryItem.nth(index));
    }

    public int getCartSize() {
        return inventoryItem.count();
    }

    @Step("Click Continue button")
    public InventoryPage clickContinueButton() {
        continueButton.click();
        return new InventoryPage(page);
    }

    @Step("Click Checkout button")
    public CheckoutStepOnePage clickCheckoutButton() {
        checkoutButton.click();
        return new CheckoutStepOnePage(page);
    }

}
