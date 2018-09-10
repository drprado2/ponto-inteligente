package com.drprado.pontointeligente.domain.querySpecifications;

import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilterField;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericOrder;
import com.drprado.pontointeligente.domain.entities.EntidadeBase;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface QuerySpecificator<T extends EntidadeBase> {

    Specification<T> getFilterSpec(GenericFilterField field);

    Specification<T> getOrderSpec(List<GenericOrder> order);
}
