package com.example.playwrightdemo.api.models.auth;

public record LoginRequest(
        String username,
        String password
) {
    @Override
    public String toString() {
        return username + "/" + password;
    }
}
