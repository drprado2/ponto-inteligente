package com.drprado.pontointeligente.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class GenericFilter {
    private FilterConnectionType filterConnectionType;
    private List<GenericFilterField> fields;

    public GenericFilter(FilterConnectionType filterConnectionType, List<GenericFilterField> fields) {
        this.filterConnectionType = filterConnectionType;
        this.fields = fields;
    }

    public GenericFilter() {
        filterConnectionType = FilterConnectionType.AND;
        fields = new ArrayList<>();
    }

    public FilterConnectionType getFilterConnectionType() {
        return filterConnectionType;
    }

    public void setFilterConnectionType(FilterConnectionType filterConnectionType) {
        this.filterConnectionType = filterConnectionType;
    }

    public List<GenericFilterField> getFields() {
        return fields;
    }

    public void setFields(List<GenericFilterField> fields) {
        this.fields = fields;
    }
}
