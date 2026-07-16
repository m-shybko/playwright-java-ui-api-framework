package com.example.playwrightdemo.api.clients;

import com.example.playwrightdemo.api.models.product.ProductRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Step;

public class ProductClient extends BaseClient {

    public ProductClient(APIRequestContext requestContext) {
        super(requestContext);
    }

    @Step("Get all products")
    public APIResponse getAllProducts() {
        return get("/products");
    }

    @Step("Get product with id {productId}")
    public APIResponse getProductById(int productId) {
        return get("/products/" + productId);
    }

    @Step("Add new product")
    public APIResponse addProduct(ProductRequest productRequest) {
        return post("/products", productRequest);
    }

    @Step("Update product with id {productId}")
    public APIResponse updateProduct(ProductRequest productRequest, int productId) {
        return put("/products/" + productId, productRequest);
    }

    @Step("Delete product with id {productId}")
    public APIResponse deleteProduct(int productId) {
        return delete("/products/" + productId);
    }
}
