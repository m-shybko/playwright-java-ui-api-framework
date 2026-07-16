package com.example.playwrightdemo.ui.tests;

import com.example.playwrightdemo.ui.data.TestUsers;
import com.example.playwrightdemo.ui.pages.CartPage;
import com.example.playwrightdemo.ui.pages.CheckoutStepOnePage;
import com.example.playwrightdemo.ui.pages.InventoryPage;
import com.example.playwrightdemo.ui.pages.LoginPage;
import com.example.playwrightdemo.ui.pages.components.InventoryItem;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


@Epic("UI")
@Feature("Cart")
public class CartTests extends BaseTest {

    @BeforeEach
    @DisplayName("Login as standard user")
    public void login() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.open();
        loginPage.login(TestUsers.STANDARD);
    }

    @Test
    @DisplayName("Check item in cart")
    public void addedItemInCartTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        InventoryItem item = inventoryPage.getItem(0);
        String expectedItemName = item.getItemName();
        item.clickAddButton();

        CartPage cartPage = inventoryPage.goToCart();
        Assertions.assertEquals(expectedItemName, cartPage.getItem(0).getItemName());
    }

    @Test
    @DisplayName("Remove item from cart")
    public void removeItemFromCartTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        InventoryItem item = inventoryPage.getItem(0);
        item.clickAddButton();

        CartPage cartPage = inventoryPage.goToCart();
        Assertions.assertEquals(1, cartPage.getCartSize());
        cartPage.getItem(0).clickRemoveButton();
        Assertions.assertEquals(0, cartPage.getCartSize());
    }

    @Test
    @DisplayName("Check when few items in cart")
    public void fewItemsInCartTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.getItem(0).clickAddButton();
        inventoryPage.getItem(1).clickAddButton();

        CartPage cartPage = inventoryPage.goToCart();
        Assertions.assertEquals(2, cartPage.getCartSize());

    }

    @Test
    @DisplayName("Check \"continue shopping\" button")
    public void continueShoppingTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.getItem(0).clickAddButton();

        CartPage cartPage = inventoryPage.goToCart();

        inventoryPage = cartPage.clickContinueButton();
        assertThat(page).hasURL(InventoryPage.URL);
        assertThat(inventoryPage.getCartIconBadge()).isVisible();
    }

    @Test
    @DisplayName("Check \"checkout\" button")
    public void checkoutButtonTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.getItem(0).clickAddButton();

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutStepOnePage checkoutStepOnePage = cartPage.clickCheckoutButton();
        assertThat(page).hasURL(CheckoutStepOnePage.URL);
        assertThat(checkoutStepOnePage.getFirstNameInput()).isVisible();
    }
}
