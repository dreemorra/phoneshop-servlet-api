package com.es.phoneshop.services;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDemoData;
import com.es.phoneshop.services.impl.DefaultRecentlyViewedProductsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRecentlyViewedProductsServiceTest {

    private DefaultRecentlyViewedProductsService service;

    @Mock
    HttpServletRequest req;
    @Mock
    HttpSession httpSession;

    LinkedList<Product> productLinkedList;

    @Before
    public void setup() {
        service = DefaultRecentlyViewedProductsService.getInstance();
        productLinkedList = new LinkedList<>();
        ProductDemoData.saveSampleProducts();
        Mockito.when(req.getSession()).thenReturn(httpSession);
        Mockito.when(httpSession.getAttribute(Mockito.anyString())).thenReturn(productLinkedList);
    }

    @Test
    public void testAddProduct() {
        Product product = ArrayListProductDao.getInstance().getProduct(1L);
        service.addProduct(req, product);
        assertTrue(productLinkedList.contains(product));
    }
}