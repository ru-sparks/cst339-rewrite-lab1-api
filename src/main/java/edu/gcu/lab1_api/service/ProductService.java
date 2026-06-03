package edu.gcu.lab1_api.service;

import edu.gcu.lab1_api.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDto> findAll();

    Optional<ProductDto> findById(Long id);

    ProductDto create(ProductDto productDto);

    Optional<ProductDto> update(Long id, ProductDto productDto);

    boolean delete(Long id);
}
