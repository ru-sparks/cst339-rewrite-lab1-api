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
