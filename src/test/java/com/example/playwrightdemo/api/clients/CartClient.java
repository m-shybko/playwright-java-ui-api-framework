package com.example.playwrightdemo.api.clients;

import com.example.playwrightdemo.api.models.cart.CartRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Step;

public class CartClient extends BaseClient {

    public CartClient(APIRequestContext requestContext) {
        super(requestContext);
    }

    @Step("Get all carts")
    public APIResponse getAllCarts() {
        return get("/carts");
    }

    @Step("Get cart with id {cartId}")
    public APIResponse getCartById(int cartId) {
        return get("/carts/" + cartId);
    }

    @Step("Add new cart")
    public APIResponse addCart(CartRequest cartRequest) {
        return post("/carts", cartRequest);
    }

    @Step("Update cart with id {cartId}")
    public APIResponse updateCart(CartRequest cartRequest, int cartId) {
        return put("/carts/" + cartId, cartRequest);
    }

    @Step("Delete cart with id {cartId}")
    public APIResponse deleteCart(int cartId) {
        return delete("/carts/" + cartId);
    }
}
