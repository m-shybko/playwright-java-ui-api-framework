package com.example.playwrightdemo.ui.tests;

import com.example.playwrightdemo.ui.data.TestUsers;
import com.example.playwrightdemo.ui.pages.*;
import com.example.playwrightdemo.ui.pages.components.InventoryItem;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Epic("UI")
@Feature("Checkout")
public class CheckoutTests extends BaseTest {

    @BeforeEach
    @DisplayName("Login as standard user")
    public void login() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.open();
        loginPage.login(TestUsers.STANDARD);
    }

    @ParameterizedTest
    @DisplayName("Check empty checkout fields errors")
    @CsvSource({
            "'',    valid, valid, First Name",
            "valid, '',    valid, Last Name",
            "valid, valid, '',    Postal Code",
            "'',    '',    '',    First Name"
    })
    public void emptyRequiredFieldsTest(String firstName, String lastName, String postalCode, String errorField) {
        InventoryPage inventoryPage = new InventoryPage(page);

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutStepOnePage checkoutStepOnePage = cartPage.clickCheckoutButton();
        checkoutStepOnePage.continueWithFields(firstName, lastName, postalCode);
        assertThat(checkoutStepOnePage.getErrorMessage()).hasText("Error: %s is required".formatted(errorField));

    }

    @Test
    @DisplayName("Go to Checkout step 2")
    public void continueToStepTwoTest() {
        InventoryPage inventoryPage = new InventoryPage(page);

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutStepOnePage checkoutStepOnePage = cartPage.clickCheckoutButton();

        CheckoutStepTwoPage checkoutStepTwoPage = checkoutStepOnePage.continueWithValidFields();
        assertThat(page).hasURL(CheckoutStepTwoPage.URL);
        assertThat(checkoutStepTwoPage.getSubtotalLabel()).isVisible();
    }

    @Test
    @DisplayName("Cancel checkout from step 1")
    public void stepOneCancelTest() {
        InventoryPage inventoryPage = new InventoryPage(page);

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutStepOnePage checkoutStepOnePage = cartPage.clickCheckoutButton();
        checkoutStepOnePage.clickCancelButton();
        assertThat(page).hasURL(CartPage.URL);
    }

    @Test
    @DisplayName("Cancel checkout from step 2")
    public void stepTwoCancelTest() {
        InventoryPage inventoryPage = new InventoryPage(page);

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutStepOnePage checkoutStepOnePage = cartPage.clickCheckoutButton();

        CheckoutStepTwoPage checkoutStepTwoPage = checkoutStepOnePage.continueWithValidFields();
        checkoutStepTwoPage.clickCancelButton();
        assertThat(page).hasURL(InventoryPage.URL);
    }

    @Test
    @DisplayName("Check added item at Checkout page")
    public void addedItemInCheckoutTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        InventoryItem item = inventoryPage.getItem(0);
        item.clickAddButton();
        String expectedItemName = item.getItemName();

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutStepOnePage checkoutStepOnePage = cartPage.clickCheckoutButton();

        CheckoutStepTwoPage checkoutStepTwoPage = checkoutStepOnePage.continueWithValidFields();
        Assertions.assertEquals(expectedItemName, checkoutStepTwoPage.getItem(0).getItemName());
    }

    @Test
    @DisplayName("Check subtotal price")
    public void subtotalPriceTest() {
        double expectedSubtotalPrice = 0;

        InventoryPage inventoryPage = new InventoryPage(page);
        InventoryItem item1 = inventoryPage.getItem(0);
        item1.clickAddButton();
        expectedSubtotalPrice += item1.getItemPrice();
        InventoryItem item2 = inventoryPage.getItem(1);
        item2.clickAddButton();
        expectedSubtotalPrice += item2.getItemPrice();

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutStepOnePage checkoutStepOnePage = cartPage.clickCheckoutButton();

        CheckoutStepTwoPage checkoutStepTwoPage = checkoutStepOnePage.continueWithValidFields();
        Assertions.assertEquals(expectedSubtotalPrice, checkoutStepTwoPage.getSubtotalAmount());

    }

    @Test
    @DisplayName("Full checkout test")
    public void fullCheckoutTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.getItem(0).clickAddButton();

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutStepOnePage checkoutStepOnePage = cartPage.clickCheckoutButton();

        CheckoutStepTwoPage checkoutStepTwoPage = checkoutStepOnePage.continueWithValidFields();

        CheckoutCompletePage checkoutCompletePage = checkoutStepTwoPage.clickFinishButton();
        assertThat(page).hasURL(CheckoutCompletePage.URL);
        assertThat(checkoutCompletePage.getMainText()).isVisible();
        assertThat(checkoutCompletePage.cartIconBadge()).isHidden();
    }

    @Test
    @DisplayName("Check \"back home\" button")
    public void backHomeTest() {
        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.getItem(0).clickAddButton();

        CartPage cartPage = inventoryPage.goToCart();

        CheckoutStepOnePage checkoutStepOnePage = cartPage.clickCheckoutButton();

        CheckoutStepTwoPage checkoutStepTwoPage = checkoutStepOnePage.continueWithValidFields();

        CheckoutCompletePage checkoutCompletePage = checkoutStepTwoPage.clickFinishButton();
        checkoutCompletePage.clickHomeButton();
        assertThat(page).hasURL(InventoryPage.URL);
    }

}
