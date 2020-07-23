package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDemoData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private Product product;
    @Mock
    private ProductDao productDao;
    @Mock
    private ServletConfig config;


    private final ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();


    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        ProductDemoData.saveSampleProducts();
        productDao = ArrayListProductDao.getInstance();
        product = productDao.getProduct(1L);
        
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getRequestDispatcher("/WEB-INF/pages/product.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute("product", product);
        verify(requestDispatcher, times(1)).forward(request, response);
    }



}