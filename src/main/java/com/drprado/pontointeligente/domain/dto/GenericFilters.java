package com.drprado.pontointeligente.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class GenericFilters {
    private List<GenericFilter> filters;
    private List<GenericOrder> orders;

    public List<GenericFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<GenericFilter> filters) {
        this.filters = filters;
    }

    public void addFilter(GenericFilter filter){
        this.filters.add(filter);
    }

    public List<GenericOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<GenericOrder> orders) {
        this.orders = orders;
    }

    public void addOrder(GenericOrder order){
        orders.add(order);
    }

    public GenericFilters(List<GenericFilter> filters, List<GenericOrder> orders) {
        this.filters = filters;
        this.orders = orders;
    }

    public GenericFilters() {
        filters = new ArrayList<>();
        orders = new ArrayList<>();
    }
}
