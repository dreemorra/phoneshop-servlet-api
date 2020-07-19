package com.es.phoneshop.model.product;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private static ProductDao instance;
    private long maxId;
    private List<Product> products;

    public static synchronized ProductDao getInstance() {
        if (instance == null) {
            instance = new ArrayListProductDao();
        }
        return instance;
    }

    private ArrayListProductDao() {
        this.products = new ArrayList<>();
    }

    @Override
    public synchronized Product getProduct(Long id) {
        return products.stream()
                .filter(product -> id.equals(product.getId()))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public synchronized List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder) {
        List<Product> foundProducts = products;
        // search
        if (query != null && !query.isEmpty()) {
            foundProducts = search(query);
        }

        Comparator<Product> comparator = Comparator.comparing(product -> {
            if (SortField.description == sortField) {
                return (Comparable) product.getDescription();
            } else {
                return (Comparable) product.getPrice();
            }
        });
        if (sortOrder == SortOrder.desc) {
            comparator = comparator.reversed();
        }

        return foundProducts.stream()
                .filter(product -> product.getPrice() != null)
                .filter(product -> product.getStock() > 0)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> search(String query) {
        String[] split = query.toLowerCase().split(" ");
        //TODO: make it better if possible
        Comparator<Product> relevanceComparator = (first, second) -> {
            int firstCounter = 0, secondCounter = 0;
            for (String q : split) {
                if (first.getDescription().toLowerCase().contains(q))
                    firstCounter++;
                if (second.getDescription().toLowerCase().contains(q))
                    secondCounter++;
            }
            return secondCounter - firstCounter;
        };

        List<Product> foundProducts = products.stream()
                .filter(p -> Arrays.stream(split).anyMatch(s -> p.getDescription().toLowerCase().contains(s)))
                .collect(Collectors.toList());
        foundProducts.sort(relevanceComparator);
        return foundProducts;
    }

    @Override
    public synchronized void save(Product product) {
        if (product.getId() != null) {
            Optional<Product> optionalProduct = products.stream()
                    .filter(o -> o.getId().equals(product.getId()))
                    .findAny();
            if (optionalProduct.isPresent()) {
                Product updatedProduct = optionalProduct.get();
                updateProduct(updatedProduct, product);
            } else {
                throw new NoSuchElementException("Unable to find product with given id");
            }
        }
        else {
            product.setId(maxId++);
            products.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        products.removeIf(product -> id.equals(product.getId()));
    }

    private void updateProduct(Product oldProduct, Product newProduct) {
        newProduct.setCode(oldProduct.getCode());
        newProduct.setDescription(oldProduct.getDescription());
        newProduct.setPriceList(oldProduct.getPriceList());
        newProduct.setStock(oldProduct.getStock());
        newProduct.setImageUrl(oldProduct.getImageUrl());
    }
}
