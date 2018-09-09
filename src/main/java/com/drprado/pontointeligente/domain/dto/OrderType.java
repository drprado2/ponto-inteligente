package com.drprado.pontointeligente.domain.dto;

public enum OrderType {
    ASC(0),
    DESC(1);

    public int value;
    OrderType(int value) {
        this.value = value;
    }
}
