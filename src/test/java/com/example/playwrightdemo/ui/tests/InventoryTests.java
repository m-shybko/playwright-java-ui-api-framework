package com.example.playwrightdemo.ui.tests;

import com.example.playwrightdemo.ui.data.TestUsers;
import com.example.playwrightdemo.ui.enums.SortOption;
import com.example.playwrightdemo.ui.pages.CartPage;
import com.example.playwrightdemo.ui.pages.InventoryItemPage;
import com.example.playwrightdemo.ui.pages.InventoryPage;
import com.example.playwrightdemo.ui.pages.LoginPage;
import com.example.playwrightdemo.ui.pages.components.InventoryItem;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Epic("UI")
@Feature("Inventory")
public class InventoryTests extends BaseTest {

    @BeforeEach
    @DisplayName("Login as standard user")
    public void login() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.open();
        loginPage.login(TestUsers.STANDARD);
    }

    @Test
    @DisplayName("Sort items by name desc")
    public void sortNameDescTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.selectFilter(SortOption.NAME_DESC);

        List<String> actualNames = inventoryPage.getItemsNames();
        List<String> expectedNames = new ArrayList<>(actualNames);

        expectedNames.sort(Comparator.reverseOrder());
        Assertions.assertEquals(expectedNames, actualNames);
    }

    @Test
    @DisplayName("Sort items by name asc")
    public void sortNameAscTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.selectFilter(SortOption.NAME_DESC);
        inventoryPage.selectFilter(SortOption.NAME_ASC);

        List<String> actualNames = inventoryPage.getItemsNames();
        List<String> expectedNames = new ArrayList<>(actualNames);

        expectedNames.sort(Comparator.naturalOrder());
        Assertions.assertEquals(expectedNames, actualNames);
    }

    @Test
    @DisplayName("Sort items by price desc")
    public void sortPriceDescTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.selectFilter(SortOption.PRICE_DESC);

        List<Double> actualPrices = inventoryPage.getItemsPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);

        expectedPrices.sort(Comparator.reverseOrder());
        Assertions.assertEquals(expectedPrices, actualPrices);
    }

    @Test
    @DisplayName("Sort items by price asc")
    public void sortPriceAscTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.selectFilter(SortOption.PRICE_ASC);

        List<Double> actualPrices = inventoryPage.getItemsPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);

        expectedPrices.sort(Comparator.naturalOrder());
        Assertions.assertEquals(expectedPrices, actualPrices);
    }

    @Test
    @DisplayName("Add item to cart")
    public void addToCartTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        InventoryItem item = inventoryPage.getItem(0);
        item.clickAddButton();
        assertThat(inventoryPage.getCartIconBadge()).isVisible();
    }

    @Test
    @DisplayName("Remove item from cart")
    public void removeFromCartTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        InventoryItem item = inventoryPage.getItem(0);
        item.clickAddButton();
        assertThat(inventoryPage.getCartIconBadge()).isVisible();
        item.clickRemoveButton();
        assertThat(inventoryPage.getCartIconBadge()).isHidden();
    }

    @Test
    @DisplayName("Go to Item page")
    public void openItemPageTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        InventoryItem item = inventoryPage.getItem(0);
        String expectedItemName = item.getItemName();
        item.clickOnItem();
        Assertions.assertTrue(page.url().contains(InventoryItemPage.PATH));

        InventoryItemPage itemPage = new InventoryItemPage(page);
        Assertions.assertEquals(expectedItemName, itemPage.getItemName());
    }

    @Test
    @DisplayName("Open Cart page")
    public void openCartPageTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        InventoryItem item = inventoryPage.getItem(0);
        item.clickAddButton();
        inventoryPage.goToCart();
        assertThat(page).hasURL(CartPage.URL);

        CartPage cartPage = new CartPage(page);
        assertThat(cartPage.getCartList()).isVisible();
    }

}
