package com.example.playwrightdemo.api.tests;

import com.example.playwrightdemo.api.clients.CartClient;
import com.example.playwrightdemo.api.models.cart.CartRequest;
import com.example.playwrightdemo.api.models.cart.CartProduct;
import com.example.playwrightdemo.api.models.cart.CartResponse;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("API")
@Feature("Carts")
public class CartTests extends BaseApiTest {

    private CartClient cartClient;

    @BeforeEach
    void setUpClient() {
        cartClient = new CartClient(requestContext);
    }

    @Test
    @DisplayName("Get all carts")
    void getAllCartsTest() throws IOException {
        APIResponse response = cartClient.getAllCarts();
        assertEquals(200, response.status());

        CartResponse[] carts = readResponse(response, CartResponse[].class);
        assertTrue(carts[0].id() > 0);
        assertTrue(carts.length > 5);
    }

    @Test
    @DisplayName("Get cart by id")
    void getCartTest() throws IOException {
        int cartId = 5;

        APIResponse response = cartClient.getCartById(cartId);
        assertEquals(200, response.status());

        CartResponse cart = readResponse(response, CartResponse.class);
        assertEquals(cartId, cart.id());
        assertEquals("2020-03-01T00:00:00.000Z", cart.date());
        assertEquals(1, cart.products().get(1).quantity());
        assertEquals(8, cart.products().get(1).productId());
    }

    @Test
    @DisplayName("Add new cart")
    void addCartTest() throws IOException {
        CartProduct cartProduct1 = createCartProduct(111);
        CartProduct cartProduct2 = createCartProduct(999);

        List<CartProduct> cartProducts = new ArrayList<>();
        cartProducts.add(cartProduct1);
        cartProducts.add(cartProduct2);

        CartRequest cartRequest = new CartRequest(
                222,
                "2020-01-02T00:00:00.000Z",
                cartProducts
        );

        APIResponse response = cartClient.addCart(cartRequest);
        assertEquals(201, response.status());

        CartResponse cartResponse = readResponse(response, CartResponse.class);
        assertEquals(cartRequest.userId(), cartResponse.userId());
        assertEquals(cartRequest.date(), cartResponse.date());
        assertEquals(cartRequest.products(), cartResponse.products());
    }

    @Test
    @DisplayName("Update cart")
    void updateCartTest() throws IOException {
        CartProduct cartProduct1 = createCartProduct(111);
        List<CartProduct> cartProducts = new ArrayList<>();
        cartProducts.add(cartProduct1);
        int cartId = 222;

        CartRequest cartRequest = new CartRequest(
                cartId,
                "2020-01-02T00:00:00.000Z",
                cartProducts
        );

        APIResponse response = cartClient.updateCart(cartRequest, cartId);
        assertEquals(200, response.status());

        CartResponse cartResponse = readResponse(response, CartResponse.class);
        assertEquals(cartRequest.userId(), cartResponse.userId());
        assertEquals(cartRequest.date(), cartResponse.date());
        assertEquals(cartRequest.products(), cartResponse.products());
    }

    @Test
    @DisplayName("Delete cart")
    void deleteCartTest() throws IOException {
        int cartId = 5;

        APIResponse response = cartClient.deleteCart(cartId);
        assertEquals(200, response.status());

        CartResponse cartResponse = readResponse(response, CartResponse.class);
        assertEquals(cartId, cartResponse.id());
    }


    private CartProduct createCartProduct(int productId) {
        return new CartProduct(
                productId,
                2
        );
    }
}