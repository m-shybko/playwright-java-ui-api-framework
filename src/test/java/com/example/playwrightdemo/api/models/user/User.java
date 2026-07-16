package com.example.playwrightdemo.api.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"__v"})
public record User(
        Address address,
        int id,
        String email,
        String username,
        String password,
        Name name,
        String phone
) {
}