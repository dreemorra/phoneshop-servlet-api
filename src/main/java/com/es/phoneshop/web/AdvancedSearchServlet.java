package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.enums.SearchFields;
import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.product.Product;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AdvancedSearchServlet extends HttpServlet {
    private ProductDao productDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productCode = request.getParameter("productCode");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String minStock = request.getParameter("minStock");

        String field = request.getParameter("sort");
        String order = request.getParameter("order");
        request.setAttribute("sort", field);
        request.setAttribute("order", order);

        request.setAttribute("productCode", productCode);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);
        request.setAttribute("minStock", minStock);

        Map<SearchFields, String> parameters = new HashMap<>();
        parameters.put(SearchFields.PRODUCT_CODE, productCode);
        parameters.put(SearchFields.MIN_PRICE, minPrice);
        parameters.put(SearchFields.MAX_PRICE, maxPrice);
        parameters.put(SearchFields.MIN_STOCK, minStock);

        List<Product> productList = productDao.findAdvancedProducts(parameters,
                Optional.ofNullable(field).map(SortField::valueOf).orElse(null),
                Optional.ofNullable(order).map(SortOrder::valueOf).orElse(null));

        request.setAttribute("products", productList);

        request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
    }
}
