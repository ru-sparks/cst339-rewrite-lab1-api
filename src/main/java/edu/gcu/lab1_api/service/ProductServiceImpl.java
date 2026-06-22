package edu.gcu.lab1_api.service;

import edu.gcu.lab1_api.dto.ProductDto;
import edu.gcu.lab1_api.model.Product;
import edu.gcu.lab1_api.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id).map(this::toDto);
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        Product saved = productRepository.save(toEntity(productDto));
        return toDto(saved);
    }

    @Override
    public Optional<ProductDto> update(Long id, ProductDto productDto) {
        return productRepository.findById(id)
                .map(existing -> {
                    Product updated = new Product(
                            existing.getId(),
                            productDto.name(),
                            productDto.description(),
                            productDto.price(),
                            productDto.quantity(),
                            productDto.category());
                    return productRepository.save(updated);
                })
                .map(this::toDto);
    }

    @Override
    public boolean delete(Long id) {
        return productRepository.deleteById(id);
    }

    private ProductDto toDto(Product product) {
        if (product == null)
            return null;
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory());
    }

    private Product toEntity(ProductDto dto) {
        if (dto == null)
            return null;
        return new Product(
                dto.id(),
                dto.name(),
                dto.description(),
                dto.price(),
                dto.quantity(),
                dto.category());
    }
}
