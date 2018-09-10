package com.drprado.pontointeligente.domain.builders;

import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilter;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilters;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericOrder;

import java.util.ArrayList;
import java.util.List;

public final class GenericFiltersBuilder {
    private GenericFiltersBuilder() {
    }

    private List<GenericFilter> filters = new ArrayList<>();
    private List<GenericOrder> orders = new ArrayList<>();

    protected void addFilter(GenericFilter filter){
        filters.add(filter);
    }

    protected void addOrder(GenericOrder order){
        orders.add(order);
    }

    public static GenericFiltersBuilder where(GenericFilter filter){
        GenericFiltersBuilder builder = new GenericFiltersBuilder();
        builder.addFilter(filter);
        return builder;
    }

    public static GenericFiltersBuilder orderBy(GenericOrder order){
        GenericFiltersBuilder builder = new GenericFiltersBuilder();
        builder.addOrder(order);
        return builder;
    }

    public GenericFiltersBuilder andFilter(GenericFilter filter){
        addFilter(filter);
        return this;
    }

    public GenericFiltersBuilder andOrderBy(GenericOrder order){
        addOrder(order);
        return this;
    }

    public GenericFilters build(){
        return new GenericFilters(filters, orders);
    }
}
