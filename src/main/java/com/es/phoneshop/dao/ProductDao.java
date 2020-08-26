package com.es.phoneshop.dao;

import com.es.phoneshop.enums.SearchFields;
import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.product.Product;

import java.util.List;
import java.util.Map;

public interface ProductDao {
    Product getProduct(Long id);
    List<Product> findProducts(String query, SortField sortField, SortOrder sortOrder);
    List<Product> findAdvancedProducts(Map<SearchFields, String> fields, SortField sortField, SortOrder sortOrder);
    List<Product> search(String query);
    void save(Product product);
    void delete(Long id);
}
