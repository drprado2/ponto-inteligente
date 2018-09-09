package com.drprado.pontointeligente.domain.dto;

public class GenericOrder {
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public GenericOrder(String field, OrderType orderType) {
        this.field = field;
        this.orderType = orderType;
    }

    public GenericOrder() {
        field = null;
        orderType = OrderType.ASC;
    }

    private String field;
    private OrderType orderType;
}
