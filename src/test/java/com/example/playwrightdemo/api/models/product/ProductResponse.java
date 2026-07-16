package com.example.playwrightdemo.api.models.product;

public record ProductResponse(
        int id,
        String title,
        double price,
        String description,
        String category,
        String image,
        Rating rating
) {
}
