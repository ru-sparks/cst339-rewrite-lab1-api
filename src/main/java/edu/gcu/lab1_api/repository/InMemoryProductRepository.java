package edu.gcu.lab1_api.repository;

import edu.gcu.lab1_api.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> store = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idGenerator = new AtomicLong(1);

    public InMemoryProductRepository() {
        save(new Product(null, "Wireless Mouse", "Ergonomic wireless mouse", 24.99, 150, "Accessories"));
        save(new Product(null, "Mechanical Keyboard", "RGB backlit keyboard", 79.99, 80, "Accessories"));
        save(new Product(null, "USB-C Hub", "7-port USB-C hub", 39.99, 120, "Accessories"));
        save(new Product(null, "Noise Cancelling Headphones", "Over-ear noise cancelling headphones", 129.99, 45,
                "Audio"));
        save(new Product(null, "Smartphone Stand", "Adjustable desk phone stand", 14.99, 200, "Accessories"));
        save(new Product(null, "Portable Charger", "10000mAh power bank", 29.99, 110, "Power"));
        save(new Product(null, "Bluetooth Speaker", "Waterproof Bluetooth speaker", 59.99, 70, "Audio"));
        save(new Product(null, "Fitness Tracker", "Heart rate and activity tracker", 49.99, 95, "Wearables"));
        save(new Product(null, "Laptop Sleeve", "Protective laptop sleeve", 19.99, 160, "Bags"));
        save(new Product(null, "Webcam", "1080p streaming webcam", 49.99, 55, "Video"));
    }

    @Override
    public List<Product> findAll() {
        synchronized (store) {
            return new ArrayList<>(store);
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        if (id == null)
            return Optional.empty();
        synchronized (store) {
            return store.stream().filter(p -> id.equals(p.getId())).findFirst();
        }
    }

    @Override
    public Product save(Product product) {
        if (product == null)
            return null;
        synchronized (store) {
            if (product.getId() == null) {
                product.setId(idGenerator.getAndIncrement());
                store.add(product);
                return product;
            }

            // update existing
            for (int i = 0; i < store.size(); i++) {
                if (product.getId().equals(store.get(i).getId())) {
                    store.set(i, product);
                    return product;
                }
            }

            // if not found, treat as new
            store.add(product);
            return product;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null)
            return false;
        synchronized (store) {
            return store.removeIf(p -> id.equals(p.getId()));
        }
    }

    @Override
    public long count() {
        return store.size();
    }
}
