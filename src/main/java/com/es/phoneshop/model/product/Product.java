package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Product {
    private Long id;
    private String code;
    private String description;
    private List<PriceHistory> priceList;
    private int stock;
    private String imageUrl;

    public Product() {
        priceList = new ArrayList<>();
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.priceList = null;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.newPrice(new PriceHistory(price, currency));
    }

    public Product(Long id, String code, String description, List<PriceHistory> priceList, int stock, String imageUrl) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.priceList = priceList;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public Product(String code, String description, List<PriceHistory> priceList, int stock, String imageUrl) {
        this.code = code;
        this.description = description;
        this.priceList = priceList;
        this.stock = stock;
        this.imageUrl = imageUrl;
        if (this.priceList == null)
            this.priceList = new ArrayList<>();
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
    }

    public BigDecimal getPrice() {
        return priceList.size() > 0
                ? priceList.get(priceList.size() - 1).getPrice()
                : new BigDecimal(0);
    }

    public Currency getCurrency() {
        return priceList.size() > 0
                ? priceList.get(priceList.size() - 1).getCurrency()
                : Currency.getInstance("USD");
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