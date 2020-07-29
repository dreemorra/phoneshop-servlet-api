package com.es.phoneshop.services.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.services.RecentlyViewedProductsService;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

public class DefaultRecentlyViewedProductsService implements RecentlyViewedProductsService {

    private static final String RECENTLY_VIEWED_ATTRIBUTE = DefaultRecentlyViewedProductsService.class.getName() + ".viewedProducts";
    private static DefaultRecentlyViewedProductsService instance;
    private ProductDao productDao;
    private final int listSize = 3;

    public static synchronized DefaultRecentlyViewedProductsService getInstance() {
        if (instance == null) {
            instance = new DefaultRecentlyViewedProductsService();
        }
        return instance;
    }

    private DefaultRecentlyViewedProductsService() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public List<Product> getList(HttpServletRequest request) {
        LinkedList<Product> viewedProducts = (LinkedList) request.getSession().getAttribute(RECENTLY_VIEWED_ATTRIBUTE);
        if (viewedProducts == null) {
            request.getSession().setAttribute(RECENTLY_VIEWED_ATTRIBUTE, viewedProducts = new LinkedList<>());
        }
        return viewedProducts;
    }

    @Override
    public void addProduct(HttpServletRequest req, Product product) {
        LinkedList<Product> productList = (LinkedList<Product>) getList(req);
        if (productList.contains(product)) {
            productList.remove(product);
        } else if (productList.size() >= listSize) {
            productList.removeLast();
        }
        productList.addFirst(product);
        req.getSession().setAttribute(RECENTLY_VIEWED_ATTRIBUTE, productList);
    }
}
