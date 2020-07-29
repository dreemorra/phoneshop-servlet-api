package com.es.phoneshop.web;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.product.*;
import com.es.phoneshop.services.impl.DefaultRecentlyViewedProductsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ProductListPageServlet extends HttpServlet {
    private ProductDao productDao;
    private DefaultRecentlyViewedProductsService recentlyViewedService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        recentlyViewedService = DefaultRecentlyViewedProductsService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String field = request.getParameter("sort");
        String order = request.getParameter("order");
        request.setAttribute("sort", field);
        request.setAttribute("order", order);
        request.setAttribute("query", query);
        request.setAttribute("recentlyViewedProducts", recentlyViewedService.getList(request));

        List<Product> productList = productDao.findProducts(query,
                Optional.ofNullable(field).map(SortField::valueOf).orElse(null),
                Optional.ofNullable(order).map(SortOrder::valueOf).orElse(null));

        request.setAttribute("products", productList);

        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
