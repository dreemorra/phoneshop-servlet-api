package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDemoData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

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
    private ServletConfig config;
    @Mock
    private HttpSession httpSession;

    private ProductDao productDao;
    private ProductDetailsPageServlet servlet;

    @Before
    public void setup() throws Exception {
        servlet = new ProductDetailsPageServlet();
        servlet.init(config);
        Mockito.when(request.getSession()).thenReturn(httpSession);
        Mockito.when(httpSession.getAttribute(Mockito.anyString())).thenReturn(null);
        when(request.getPathInfo()).thenReturn("/0");
        ProductDemoData.saveSampleProducts();
        productDao = ArrayListProductDao.getInstance();
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getLocale()).thenReturn(new Locale("en"));
        when(request.getParameter("quantity")).thenReturn("2");
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute( ArgumentMatchers.eq("product"), ArgumentMatchers.any(Product.class));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);
        verify(response).sendRedirect(Mockito.anyString());
    }

}