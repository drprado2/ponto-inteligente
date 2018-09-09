package com.drprado.pontointeligente.domain.dto;

public enum FilterConnectionType {
    AND(0),
    OR(1);

    public int value;

    FilterConnectionType(int value) {
        this.value = value;
    }
}
