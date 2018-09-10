package com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI;

public enum FilterConnectionType {
    AND(0),
    OR(1);

    public int value;

    FilterConnectionType(int value) {
        this.value = value;
    }
}
