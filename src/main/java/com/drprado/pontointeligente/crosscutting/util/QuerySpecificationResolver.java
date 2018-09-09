package com.drprado.pontointeligente.crosscutting.util;

import com.drprado.pontointeligente.domain.dto.FilterConnectionType;
import com.drprado.pontointeligente.domain.dto.GenericFilter;
import com.drprado.pontointeligente.domain.dto.GenericFilters;
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

        Specification<T> finalSpec = QuerySpecHelper.specAlways();

        for(int i = 0; i < filters.getFilters().size(); i++){
            GenericFilter currentFilter = filters.getFilters().get(i);
            Specification<T> currentSpec = QuerySpecHelper.specAlways();

            List<Specification<T>> specs = currentFilter.getFields()
                    .stream()
                    .map(f -> specificator.getFilterSpec(f))
                    .collect(Collectors.toList());

            if(currentFilter.getFilterConnectionType() == FilterConnectionType.AND) {
                for(Specification<T> sp : specs)
                    currentSpec = currentSpec.and(sp);
            }else{
                for(Specification<T> sp : specs)
                    currentSpec = currentSpec.or(sp);
            }

            if(i == 0)
                finalSpec = Specification.where(currentSpec);
            else
                finalSpec = finalSpec.or(currentSpec);
        }

        return finalSpec;
    }

    public static <T extends EntidadeBase> Specification<T> resolveOrders(
            QuerySpecificator<T> specificator, GenericFilters filters) {

        return specificator.getOrderSpec(filters.getOrders());
    }
}
