package com.example.demo.Dtos;

import jakarta.validation.constraints.NotBlank;

public record TaskUpdate(
        @NotBlank(message = "Status is required")
        String status
) {
}
