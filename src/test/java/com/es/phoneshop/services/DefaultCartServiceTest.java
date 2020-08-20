package com.es.phoneshop.services;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.services.impl.DefaultCartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ ArrayListProductDao.class })
public class DefaultCartServiceTest {

    private DefaultCartService service;

    private ArrayListProductDao productDao = mock(ArrayListProductDao.class);

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpSession httpSession;
    @Mock
    private Cart cart;
    @Mock
    private Product product;

    @Before
    public void init() {
        service = DefaultCartService.getInstance();
        product = new Product(0L,"sgs", "Samsung Galaxy S", new PriceHistory(new BigDecimal(110), Currency.getInstance("USD"), new Date(100 + 20, 3, 11)), 100, "manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");

        Mockito.when(req.getSession()).thenReturn(httpSession);
        Mockito.when(httpSession.getAttribute(Mockito.anyString())).thenReturn(cart);
        Mockito.when(productDao.getProduct(Mockito.anyLong())).thenReturn(product);
    }

    @Test
    public void testAddProduct() throws OutOfStockException {
        cart = new Cart();
//        Product product = ArrayListProductDao.getInstance().getProduct(1L);
        service.add(cart, 0L, 3);
        assertTrue(cart.getItems().contains(product));
    }
}