package com.example.playwrightdemo.api.models.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties({"__v"})
public record CartResponse(
        int id,
        int userId,
        String date,
        List<CartProduct> products
) {
}