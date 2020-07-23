package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Product {
    private Long id;
    private String code;
    private String description;
    private PriceHistory recentPrice;
    private List<PriceHistory> priceList;
    private int stock;
    private String imageUrl;

    public Product() {
        priceList = new ArrayList<>();
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this(code, description, price, currency, stock, imageUrl);
        this.id = id;
    }

    public Product(Long id, String code, String description, PriceHistory price, int stock, String imageUrl) {
        this(code, description, price, stock, imageUrl);
        this.id = id;
    }

    public Product(String code, String description, PriceHistory price, int stock, String imageUrl) {
        this.code = code;
        this.description = description;
        this.priceList = new ArrayList<>();
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.newPrice(price);
    }

    public Product(String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.code = code;
        this.description = description;
        this.priceList = new ArrayList<>();
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.newPrice(new PriceHistory(price, currency));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PriceHistory> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<PriceHistory> prices) {
        this.priceList = prices;
    }

    public void newPrice(PriceHistory price) {
        priceList.add(price);
        recentPrice = price;
    }

    public BigDecimal getPrice() {
        return recentPrice.getPrice();
    }

    public Currency getCurrency() {
        return recentPrice.getCurrency();
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}