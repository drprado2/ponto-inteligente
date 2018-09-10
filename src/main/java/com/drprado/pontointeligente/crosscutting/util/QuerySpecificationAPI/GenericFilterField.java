package com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI;

public class GenericFilterField<T extends Object> {
    public GenericFilterField(String fieldName, T fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public GenericFilterField() {
        fieldName = null;
        fieldValue = null;
    }

    private String fieldName;
    private T fieldValue;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public T getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(T fieldValue) {
        this.fieldValue = fieldValue;
    }
}
