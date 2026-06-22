package edu.gcu.lab1_api.service;

import edu.gcu.lab1_api.dto.ProductDto;
import edu.gcu.lab1_api.model.Product;
import edu.gcu.lab1_api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void findAll_returnsMappedProductDtos() {
        Product product = new Product(1L, "Widget", "A widget", 19.99, 10, "Tools");
        given(productRepository.findAll()).willReturn(List.of(product));

        List<ProductDto> dtos = productService.findAll();

        assertEquals(1, dtos.size());
        ProductDto dto = dtos.get(0);
        assertEquals(1L, dto.id());
        assertEquals("Widget", dto.name());
        assertEquals("A widget", dto.description());
        assertEquals(19.99, dto.price());
        assertEquals(10, dto.quantity());
        assertEquals("Tools", dto.category());
    }

    @Test
    void findById_existingId_returnsDto() {
        Product product = new Product(2L, "Gadget", "A gadget", 29.99, 5, "Electronics");
        given(productRepository.findById(2L)).willReturn(Optional.of(product));

        Optional<ProductDto> result = productService.findById(2L);

        assertTrue(result.isPresent());
        assertEquals("Gadget", result.get().name());
    }

    @Test
    void findById_missingId_returnsEmpty() {
        given(productRepository.findById(999L)).willReturn(Optional.empty());

        Optional<ProductDto> result = productService.findById(999L);

        assertTrue(result.isEmpty());
    }

    @Test
    void create_savesProductAndReturnsDto() {
        ProductDto dto = new ProductDto(null, "New Item", "New item description", 12.50, 20, "Office");
        Product savedEntity = new Product(3L, "New Item", "New item description", 12.50, 20, "Office");
        given(productRepository.save(any(Product.class))).willReturn(savedEntity);

        ProductDto result = productService.create(dto);

        assertNotNull(result);
        assertEquals(3L, result.id());
        assertEquals("New Item", result.name());
        then(productRepository).should(times(1)).save(any(Product.class));
    }

    @Test
    void update_existingProduct_returnsUpdatedDto() {
        ProductDto updateDto = new ProductDto(null, "Updated", "Updated desc", 22.00, 15, "Office");
        Product existing = new Product(4L, "Old", "Old desc", 18.00, 8, "Office");
        Product updatedEntity = new Product(4L, "Updated", "Updated desc", 22.00, 15, "Office");

        given(productRepository.findById(4L)).willReturn(Optional.of(existing));
        given(productRepository.save(any(Product.class))).willReturn(updatedEntity);

        Optional<ProductDto> result = productService.update(4L, updateDto);

        assertTrue(result.isPresent());
        assertEquals(4L, result.get().id());
        assertEquals("Updated", result.get().name());
        then(productRepository).should(times(1)).findById(4L);
        then(productRepository).should(times(1)).save(any(Product.class));
    }

    @Test
    void update_missingProduct_returnsEmpty() {
        ProductDto updateDto = new ProductDto(null, "Updated", "Updated desc", 22.00, 15, "Office");
        given(productRepository.findById(5L)).willReturn(Optional.empty());

        Optional<ProductDto> result = productService.update(5L, updateDto);

        assertTrue(result.isEmpty());
        then(productRepository).should(times(1)).findById(5L);
        then(productRepository).should(times(0)).save(any(Product.class));
    }

    @Test
    void delete_delegatesToRepository() {
        given(productRepository.deleteById(6L)).willReturn(true);

        boolean deleted = productService.delete(6L);

        assertTrue(deleted);
        then(productRepository).should(times(1)).deleteById(eq(6L));
    }
}
