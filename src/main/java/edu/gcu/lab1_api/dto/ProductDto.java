package edu.gcu.lab1_api.dto;

public record ProductDto(
        Long id,
        String name,
        String description,
        Double price,
        Integer quantity,
        String category) {
}
