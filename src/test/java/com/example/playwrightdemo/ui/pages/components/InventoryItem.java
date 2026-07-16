package com.example.playwrightdemo.ui.pages.components;

import com.microsoft.playwright.Locator;

public record InventoryItem(Locator root) {

    public String getItemName() {
        return root.locator("div[data-test='inventory-item-name']").textContent();
    }

    public double getItemPrice() {
        String priceString = root.locator("div[data-test='inventory-item-price']").textContent();
        return Double.parseDouble(priceString.substring(1));
    }

    public void clickOnItem() {
        root.locator("div[data-test='inventory-item-name']").click();
    }

    public void clickAddButton() {
        root.locator("button[data-test^='add-to-cart']").click();
    }

    public void clickRemoveButton() {
        root.locator("button[data-test^='remove']").click();
    }
}
