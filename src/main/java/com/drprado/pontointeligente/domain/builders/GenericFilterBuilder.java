package com.drprado.pontointeligente.domain.builders;

import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.FilterConnectionType;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilter;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilterField;

import java.util.ArrayList;
import java.util.List;

public final class GenericFilterBuilder {
    private GenericFilterBuilder() {
    }

    private List<GenericFilterField> filters = new ArrayList<>();
    private FilterConnectionType innerLink;
    private FilterConnectionType nextLink;

    protected void setInnerLink(FilterConnectionType type){
        this.innerLink = type;
    }

    protected void setNextLink(FilterConnectionType type){
        this.nextLink = type;
    }

    protected void addFilter(GenericFilterField filter){
        filters.add(filter);
    }

    public static GenericFilterBuilder of(FilterConnectionType logicalLinkNextBlock, FilterConnectionType logicalLinkInner){
        GenericFilterBuilder builder = new GenericFilterBuilder();
        builder.setNextLink(logicalLinkNextBlock);
        builder.setInnerLink(logicalLinkInner);
        return builder;
    }

    public GenericFilterBuilder withFilter(GenericFilterField fieldFilter){
        filters.add(fieldFilter);
        return this;
    }

    public GenericFilter build(){
        return new GenericFilter(innerLink, nextLink, filters);
    }
}
