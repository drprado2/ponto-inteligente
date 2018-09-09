package com.drprado.pontointeligente.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class GenericFilter {
    private FilterConnectionType logicalLinkInnerConditions;
    private FilterConnectionType logicalLinkNextBlockConditions;
    private List<GenericFilterField> fields;

    public FilterConnectionType getLogicalLinkNextBlockConditions() {
        return logicalLinkNextBlockConditions;
    }

    public void setLogicalLinkNextBlockConditions(FilterConnectionType logicalLinkNextBlockConditions) {
        this.logicalLinkNextBlockConditions = logicalLinkNextBlockConditions;
    }

    public GenericFilter(
            FilterConnectionType logicalLinkInnerConditions,
            FilterConnectionType logicalLinkNextBlockConditions,
            List<GenericFilterField> fields) {
        this.logicalLinkInnerConditions = logicalLinkInnerConditions;
        this.logicalLinkNextBlockConditions = logicalLinkNextBlockConditions;
        this.fields = fields;
    }

    public GenericFilter() {
        logicalLinkInnerConditions = FilterConnectionType.AND;
        fields = new ArrayList<>();
    }

    public FilterConnectionType getLogicalLinkInnerConditions() {
        return logicalLinkInnerConditions;
    }

    public void setLogicalLinkInnerConditions(FilterConnectionType logicalLinkInnerConditions) {
        this.logicalLinkInnerConditions = logicalLinkInnerConditions;
    }

    public List<GenericFilterField> getFields() {
        return fields;
    }

    public void setFields(List<GenericFilterField> fields) {
        this.fields = fields;
    }
}
