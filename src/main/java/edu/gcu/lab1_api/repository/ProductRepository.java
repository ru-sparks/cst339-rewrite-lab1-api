package edu.gcu.lab1_api.repository;

import edu.gcu.lab1_api.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    boolean deleteById(Long id);

    long count();
}
