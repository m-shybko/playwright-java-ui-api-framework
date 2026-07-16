package com.example.playwrightdemo.api.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Geolocation(
        @JsonProperty("lat")
        String latitude,
        @JsonProperty("long")
        String longitude
) {
}
