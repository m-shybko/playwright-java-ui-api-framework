package com.example.playwrightdemo.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class InventoryItemPage {
    private final Page page;
    private final Locator itemName;
    public static final String PATH = "inventory-item.html?id=";


    public InventoryItemPage(Page page) {
        this.page = page;
        itemName = page.locator("div[data-test='inventory-item-name']");
    }

    public String getItemName() {
        return itemName.textContent();
    }
}
