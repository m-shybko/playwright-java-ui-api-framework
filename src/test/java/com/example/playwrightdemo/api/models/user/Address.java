package com.example.playwrightdemo.api.models.user;

public record Address(
        Geolocation geolocation,
        String city,
        String street,
        int number,
        String zipcode
) {
}
