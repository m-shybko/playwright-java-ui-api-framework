package com.example.playwrightdemo.ui.pages;

import com.example.playwrightdemo.ui.enums.SortOption;
import com.example.playwrightdemo.ui.pages.components.InventoryItem;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage {
    private final Page page;
    private final Locator inventoryList;
    private final Locator filterSelect;
    private final Locator inventoryItemNameDivs;
    private final Locator inventoryItemPriceDivs;
    private final Locator cartIcon;
    private final Locator cartIconBadge;
    private final Locator inventoryItem;

    public static final String URL = "https://www.saucedemo.com/inventory.html";

    public InventoryPage(Page page) {
        this.page = page;
        inventoryList = page.locator("div[data-test='inventory-list']");
        filterSelect = page.locator("select[data-test='product-sort-container']");
        inventoryItemNameDivs = page.locator("div[data-test='inventory-item-name']");
        inventoryItemPriceDivs = page.locator("div[data-test='inventory-item-price']");
        cartIcon = page.locator("a[data-test='shopping-cart-link']");
        cartIconBadge = page.locator("span[data-test='shopping-cart-badge']");
        inventoryItem = page.locator("div[data-test='inventory-item']");
    }

    public Locator getInventoryList() {
        return inventoryList;
    }

    @Step("Go to Cart page")
    public CartPage goToCart() {
        cartIcon.click();
        return new CartPage(page);
    }

    public Locator getCartIconBadge() {
        return cartIconBadge;
    }

    public InventoryItem getItem(int index) {
        return new InventoryItem(inventoryItem.nth(index));
    }

    @Step("Select option {option}")
    public void selectFilter(SortOption option) {
        filterSelect.selectOption(option.value());
    }

    public List<String> getItemsNames() {
        return inventoryItemNameDivs.allTextContents();
    }

    public List<Double> getItemsPrices() {
        List<Double> prices = new ArrayList<>();
        for (String priceString : inventoryItemPriceDivs.allTextContents()) {
            prices.add(Double.parseDouble(priceString.substring(1)));
        }
        return prices;
    }
}
