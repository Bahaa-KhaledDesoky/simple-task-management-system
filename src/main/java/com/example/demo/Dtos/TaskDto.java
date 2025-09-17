package com.example.demo.Dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskDto(
        @NotBlank(message = "Title is required")
        @Size(min = 3, message = "Title must be at least 3 characters long")
        String title,
        @NotBlank(message = "Description is required")
        @Size(min = 3, message = "Description must be at least 3 characters long")
                     String description
) { }
