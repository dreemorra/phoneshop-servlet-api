package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void testSaveNewProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product("test-product", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        productDao.save(product);

        assertTrue(product.getId() > 0);
        Product result = productDao.getProduct(Long.valueOf(product.getId()));
        assertNotNull(result);
        assertEquals("test-product", result.getCode());
    }

    @Test
    public void testSaveExistingProduct() {
        Currency usd = Currency.getInstance("USD");
        Product product = new Product(3L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg");
        productDao.save(product);

        Product result = productDao.getProduct(Long.valueOf(product.getId()));
        assertNotNull(result);
        assertEquals("iphone", result.getCode());
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteExistingProduct() {
        productDao.delete(3L);
        Product result = productDao.getProduct(3L);
    }
}
