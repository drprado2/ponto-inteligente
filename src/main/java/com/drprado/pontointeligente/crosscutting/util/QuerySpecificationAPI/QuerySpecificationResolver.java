package com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI;

import com.drprado.pontointeligente.domain.entities.EntidadeBase;
import com.drprado.pontointeligente.domain.querySpecifications.QuerySpecificator;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

public final class QuerySpecificationResolver {

    private QuerySpecificationResolver() {
    }

    public static <T extends EntidadeBase> Specification<T> resolveFilters(
            QuerySpecificator<T> specificator, GenericFilters filters) {

        Specification<T> finalSpec = null;

        if(filters == null || (filters.getFilters().isEmpty() && filters.getOrders().isEmpty()))
            return QuerySpecHelper.specAlways();

        for(GenericFilter currentFilter : filters.getFilters()){
            Specification<T> currentSpec;

            List<Specification<T>> specs = currentFilter.getFields()
                    .stream()
                    .map(f -> specificator.getFilterSpec(f))
                    .filter(f -> f != null)
                    .collect(Collectors.toList());

            if(currentFilter.getLogicalLinkInnerConditions() == FilterConnectionType.AND) {
                currentSpec = specs.stream().reduce((old, current) ->
                        old == null ? Specification.where(current) : old.and(current)).get();
            }else{
                currentSpec = specs.stream().reduce((old, current) ->
                        old == null ? Specification.where(current) : old.or(current)).get();
            }

            if(finalSpec == null)
                finalSpec = Specification.where(currentSpec);
            else
                finalSpec = currentFilter.getLogicalLinkNextBlockConditions() == FilterConnectionType.AND
                        ? finalSpec.and(currentSpec)
                        : finalSpec.or(currentSpec);
        }
        return finalSpec;
    }

    public static <T extends EntidadeBase> Specification<T> resolveOrders(
            QuerySpecificator<T> specificator, GenericFilters filters) {

        return specificator.getOrderSpec(filters.getOrders());
    }
}
