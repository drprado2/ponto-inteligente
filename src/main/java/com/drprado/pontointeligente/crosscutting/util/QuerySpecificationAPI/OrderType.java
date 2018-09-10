package com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI;

public enum OrderType {
    ASC(0),
    DESC(1);

    public int value;
    OrderType(int value) {
        this.value = value;
    }
}
