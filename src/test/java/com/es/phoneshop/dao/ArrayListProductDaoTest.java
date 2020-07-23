package com.es.phoneshop.dao;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ArrayListProductDao.class)
public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        try {
            productDao = Whitebox.invokeConstructor(ArrayListProductDao.class);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product("sgs", "Samsung Galaxy S", new PriceHistory(new BigDecimal(110), usd, new Date(100 + 20, 3, 11)), 100, "manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product("sgs3", "Samsung Galaxy S III", new PriceHistory(new BigDecimal(322), usd, new Date(100 + 16, 12, 6)), 5, "manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "manufacturer/Apple/Apple%20iPhone.jpg"));
        productDao.save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "manufacturer/Apple/Apple%20iPhone%206.jpg"));
        productDao.save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        productDao.save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        productDao.save(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        productDao.save(new Product("nokia3310", "Nokia 3310", new PriceHistory(new BigDecimal(150), usd, new Date(100 + 15, 8, 31)), 100, "manufacturer/Nokia/Nokia%203310.jpg"));
        productDao.save(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "manufacturer/Palm/Palm%20Pixi.jpg"));
        productDao.save(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "manufacturer/Siemens/Siemens%20C56.jpg"));
        productDao.save(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "manufacturer/Siemens/Siemens%20C61.jpg"));
        productDao.save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }

    @Test
    public void testFindProductsNoResults() {
        assertFalse(productDao.findProducts("", null, null).isEmpty());
    }

    @Test
    public void testSearch() {
        List<Product> resultList = productDao.search("Samsung II");
        List<String> expectedList = new ArrayList<String>();
        expectedList.add("Samsung Galaxy S II");
        expectedList.add("Samsung Galaxy S III");
        expectedList.add("Samsung Galaxy S");

        for (Product product : resultList) {
            boolean result = expectedList.stream()
                    .anyMatch(description -> product.getDescription().equals(description));
            assertTrue(result);
        assertFalse(resultList.isEmpty());
        }
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

    @Test(expected = ProductNotFoundException.class)
    public void testDeleteExistingProduct() {
        productDao.delete(3L);
        Product result = productDao.getProduct(3L);
    }

    @Test
    public void testSortByDescriptionAsc() {
        List<Product> products = productDao.findProducts(null, SortField.DESCRIPTION, SortOrder.ASC);
        assertEquals("iphone", products.get(0).getCode());
        assertEquals("iphone6", products.get(1).getCode());
        assertEquals("htces4g", products.get(2).getCode());
    }

    @Test
    public void testSortByDescriptionDesc() {
        ProductDao tempProductDao = productDao;
        List<Product> products = tempProductDao.findProducts(null, SortField.DESCRIPTION, SortOrder.DESC);
        assertEquals("xperiaxz", products.get(0).getCode());
        assertEquals("sec901", products.get(1).getCode());
        assertEquals("simsxg75", products.get(2).getCode());
    }

    @Test
    public void testSortByPriceAsc() {
        ProductDao tempProductDao = productDao;
        List<Product> products = tempProductDao.findProducts(null, SortField.PRICE, SortOrder.ASC);
        assertEquals("simc56", products.get(0).getCode());
        assertEquals("simc61", products.get(1).getCode());
        assertEquals("sgs", products.get(2).getCode());
    }

    @Test
    public void testSortByPriceDesc() {
        ProductDao tempProductDao = productDao;
        List<Product> products = tempProductDao.findProducts(null, SortField.PRICE, SortOrder.DESC);
        assertEquals("iphone6", products.get(0).getCode());
        assertEquals("sec901", products.get(1).getCode());
        assertEquals("sgs3", products.get(2).getCode());
    }
}
