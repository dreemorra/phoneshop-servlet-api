package com.es.phoneshop.model.product;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class ProductDemoData {

    private ProductDemoData() {}

    public static void saveSampleProducts() {
        ProductDao productDao = ArrayListProductDao.getInstance();
        Currency usd = Currency.getInstance("USD");
        //TODO: fix imageUrl
        productDao.save(new Product("sgs", "Samsung Galaxy S", new PriceHistory(new BigDecimal(200), usd, new Date(100 + 19, 12, 27)), 100, "manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product("sgs3", "Samsung Galaxy S III", new PriceHistory(new BigDecimal(666), usd, new Date(100 + 13, 6, 6)), 5, "manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "manufacturer/Apple/Apple%20iPhone.jpg"));
        productDao.save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "manufacturer/Apple/Apple%20iPhone%206.jpg"));
        productDao.save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        productDao.save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        productDao.save(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        productDao.save(new Product("nokia3310", "Nokia 3310", new PriceHistory(new BigDecimal(400), usd, new Date(100 + 06, 6, 12)), 100, "manufacturer/Nokia/Nokia%203310.jpg"));
        productDao.save(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "manufacturer/Palm/Palm%20Pixi.jpg"));
        productDao.save(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "manufacturer/Siemens/Siemens%20C56.jpg"));
        productDao.save(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "manufacturer/Siemens/Siemens%20C61.jpg"));
        productDao.save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "manufacturer/Siemens/Siemens%20SXG75.jpg"));

        productDao.getProduct(0L).newPrice(new PriceHistory(new BigDecimal(210), usd, new Date(100 + 20, 2, 1)));
        productDao.getProduct(0L).newPrice(new PriceHistory(new BigDecimal(110), usd, new Date(100 + 20, 3, 11)));

        productDao.getProduct(2L).newPrice(new PriceHistory(new BigDecimal(534), usd, new Date(100 + 17, 10, 8)));
        productDao.getProduct(2L).newPrice(new PriceHistory(new BigDecimal(322), usd, new Date(100 + 16, 12, 6)));

        productDao.getProduct(8L).newPrice(new PriceHistory(new BigDecimal(400), usd, new Date(100 + 12, 2, 23)));
        productDao.getProduct(8L).newPrice(new PriceHistory(new BigDecimal(150), usd, new Date(100 + 15, 8, 31)));
    }
}