package com.example.playwrightdemo.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class CheckoutStepOnePage {
    private final Page page;
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator postalInput;
    private final Locator continueButton;
    private final Locator cancelButton;
    private final Locator errorMessage;

    public static final String URL = "https://www.saucedemo.com/checkout-step-one.html";

    public CheckoutStepOnePage(Page page) {
        this.page = page;
        firstNameInput = page.locator("input[data-test='firstName']");
        lastNameInput = page.locator("input[data-test='lastName']");
        postalInput = page.locator("input[data-test='postalCode']");
        continueButton = page.locator("input[data-test='continue']");
        errorMessage = page.locator("h3[data-test='error']");
        cancelButton = page.locator("button[data-test='cancel']");
    }

    public Locator getFirstNameInput() {
        return firstNameInput;
    }

    @Step("Fill all checkout fields")
    public void continueWithFields(String firstName, String lastName, String postalCode) {
        firstNameInput.fill(firstName);
        lastNameInput.fill(lastName);
        postalInput.fill(postalCode);
        continueButton.click();
    }

    @Step("Go to Checkout Step Two page")
    public CheckoutStepTwoPage continueWithValidFields() {
        firstNameInput.fill("firstName");
        lastNameInput.fill("lastName");
        postalInput.fill("postalCode");
        continueButton.click();
        return new CheckoutStepTwoPage(page);
    }

    @Step("Click Cancel button")
    public void clickCancelButton() {
        cancelButton.click();
    }

    public Locator getErrorMessage() {
        return errorMessage;
    }
}
