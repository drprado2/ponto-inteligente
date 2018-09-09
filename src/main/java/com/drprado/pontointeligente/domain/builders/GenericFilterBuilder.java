package com.drprado.pontointeligente.domain.builders;

import com.drprado.pontointeligente.domain.dto.FilterConnectionType;
import com.drprado.pontointeligente.domain.dto.GenericFilter;
import com.drprado.pontointeligente.domain.dto.GenericFilterField;

import java.util.ArrayList;
import java.util.List;

public final class GenericFilterBuilder {
    private GenericFilterBuilder() {
    }

    private List<GenericFilterField> filters = new ArrayList<>();
    private FilterConnectionType type;

    protected void setType(FilterConnectionType type){
        this.type = type;
    }

    protected void addFilter(GenericFilterField filter){
        filters.add(filter);
    }

    public static GenericFilterBuilder of(FilterConnectionType type){
        GenericFilterBuilder builder = new GenericFilterBuilder();
        builder.setType(type);
        return builder;
    }

    public GenericFilterBuilder withFilter(GenericFilterField fieldFilter){
        filters.add(fieldFilter);
        return this;
    }

    public GenericFilter build(){
        return new GenericFilter(type, filters);
    }
}
