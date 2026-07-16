package com.example.playwrightdemo.api.models.product;

public record ProductRequest(
        String title,
        double price,
        String description,
        String category,
        String image
) {
}
