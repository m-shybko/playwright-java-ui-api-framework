package com.example.playwrightdemo.api.tests;

import com.example.playwrightdemo.api.clients.ProductClient;
import com.example.playwrightdemo.api.models.product.ProductResponse;
import com.example.playwrightdemo.api.models.product.ProductRequest;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Epic("API")
@Feature("Products")
public class ProductTests extends BaseApiTest {

    private ProductClient productClient;

    @BeforeEach
    void setUpClient() {
        productClient = new ProductClient(requestContext);
    }

    @Test
    @DisplayName("Get all products")
    void getAllProductsTest() throws IOException {
        APIResponse response = productClient.getAllProducts();
        assertEquals(200, response.status());

        ProductResponse[] productResponses = readResponse(response, ProductResponse[].class);
        assertTrue(productResponses.length > 10);
        assertTrue(productResponses[0].id() > 0);
        assertNotNull(productResponses[0].title());
    }

    @Test
    @DisplayName("Get product by id")
    void getProductTest() throws IOException {
        int productId = 5;

        APIResponse response = productClient.getProductById(productId);
        assertEquals(200, response.status());

        ProductResponse productResponse = readResponse(response, ProductResponse.class);
        assertEquals(productId, productResponse.id());
        assertEquals("John Hardy Women's Legends Naga Gold & Silver Dragon Station Chain Bracelet", productResponse.title());
        assertTrue(productResponse.rating().rate() > 4);
    }

    @Test
    @DisplayName("Add new product")
    void addProductTest() throws IOException {
        ProductRequest productRequest = new ProductRequest(
                "test_title",
                999.999,
                "test_description",
                "test_category",
                "test_image"
        );

        APIResponse response = productClient.addProduct(productRequest);
        assertEquals(201, response.status());

        ProductResponse productResponse = readResponse(response, ProductResponse.class);
        assertEquals(productRequest.title(), productResponse.title());
        assertEquals(productRequest.price(), productResponse.price());
        assertEquals(productRequest.category(), productResponse.category());
        assertEquals(productRequest.image(), productResponse.image());
        assertTrue(productResponse.id() > 0);
    }

    @Test
    @DisplayName("Update product")
    void updateProductTest() throws IOException {
        ProductRequest productRequest = new ProductRequest(
                "test_title",
                999.999,
                "test_description",
                "test_category",
                "test_image"
        );
        int productId = 999;

        APIResponse response = productClient.updateProduct(productRequest, productId);
        assertEquals(200, response.status());

        ProductResponse productResponse = readResponse(response, ProductResponse.class);
        assertEquals(productId, productResponse.id());
        assertEquals(productRequest.title(), productResponse.title());
        assertEquals(productRequest.price(), productResponse.price());
        assertEquals(productRequest.category(), productResponse.category());
        assertEquals(productRequest.image(), productResponse.image());
    }

    @Test
    @DisplayName("Delete product")
    void deleteProductTest() throws IOException {
        int productId = 5;

        APIResponse response = productClient.deleteProduct(productId);
        assertEquals(200, response.status());

        ProductResponse productResponse = readResponse(response, ProductResponse.class);
        assertEquals(productId, productResponse.id());
    }
}
