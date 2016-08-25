package com.qfg.businesshelper.orders.domain.model;

import android.support.annotation.NonNull;

import com.qfg.businesshelper.utils.Formatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rbtq on 8/25/16.
 */
public class Order {
    private int id;
    private String sale;
    private long createdAt;
    private List<OrderItem> items = new ArrayList<>();
    private int totalPrice;

    public int getId() {
        return id;
    }

    public Order setId(int id) {
        this.id = id;
        return this;
    }

    public String getSale() {
        return sale;
    }

    public Order setSale(String sale) {
        this.sale = sale;
        return this;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public Order setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Order setItems(@NonNull List<OrderItem> items) {
        this.items = items;
        return this;
    }

    public Order addItem(@NonNull OrderItem item) {
        this.items.add(item);
        return this;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Order setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public static class OrderItem {
        private int barCode;
        private String title;
        private int unitPrice;
        private int count;

        public int getBarCode() {
            return barCode;
        }

        public OrderItem setBarCode(int barCode) {
            this.barCode = barCode;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public OrderItem setTitle(String title) {
            this.title = title;
            return this;
        }

        public int getUnitPrice() {
            return unitPrice;
        }

        public OrderItem setUnitPrice(int unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public int getCount() {
            return count;
        }

        public OrderItem setCount(int count) {
            this.count = count;
            return this;
        }

        @Override
        public String toString() {
            return String.format("%s %s*%d", getTitle(), Formatter.toCurrency(Formatter.toFG(getUnitPrice())), getCount());
        }
    }
}
