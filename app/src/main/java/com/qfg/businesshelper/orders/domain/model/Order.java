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
    private int totalPrice = 0;

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
        this.totalPrice = 0;
        for (OrderItem item : this.items) {
            addTotalPrice(item);
        }

        return this;
    }

    private void addTotalPrice(OrderItem item) {
        this.totalPrice += item.getUnitPrice() * item.getCount();
    }

    private void minusTotalPrice(OrderItem item) {
        this.totalPrice -= item.getUnitPrice() * item.getCount();
    }

    public Order addItem(@NonNull OrderItem item) {
        boolean bFind = false;
        for (OrderItem orderItem : this.items) {
            if (orderItem.getBarCode().equals(item.getBarCode())
                && orderItem.getUnitPrice() == item.getUnitPrice()) {
                orderItem.setCount(orderItem.getCount()+item.getCount());
                bFind = true;
                break;
            }
        }

        if (!bFind) {
            this.items.add(item);
        }

        addTotalPrice(item);
        return this;
    }

    public Order deleteItem(@NonNull OrderItem item) {
        this.items.remove(item);
        minusTotalPrice(item);
        return this;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public static class OrderItem {
        private String barCode;
        private String title;
        private int unitPrice;
        private int count;

        public String getBarCode() {
            return barCode;
        }

        public OrderItem setBarCode(String barCode) {
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
            return String.format("%s %s*%d", getTitle(), Formatter.bgToShow(getUnitPrice()), getCount());
        }
    }
}
