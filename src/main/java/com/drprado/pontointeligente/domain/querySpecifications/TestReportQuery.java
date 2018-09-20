package com.drprado.pontointeligente.domain.querySpecifications;

import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilterField;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericOrder;
import com.drprado.pontointeligente.domain.enums.TipoLancamentoHora;
import com.drprado.pontointeligente.domain.reports.TestReport;
import com.drprado.pontointeligente.domain.reports.TestReport_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.SingularAttribute;
import java.util.*;
import java.util.function.Function;

import static com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.QuerySpecHelper.getLikePattern;
import static com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.QuerySpecHelper.specAlways;
import static com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.QuerySpecHelper.specNever;

@Component
public final class TestReportQuery  implements QuerySpecificator<TestReport> {

    private Map<String, Function<Object, Specification<TestReport>>> filterFunctions;
    public Map<String, SingularAttribute<TestReport, ? extends Object>> attributes;

    public TestReportQuery() {
        filterFunctions = new HashMap<>();
        attributes = new HashMap<>();
        registerAttributes();
        registerFilterFunctions();
    }

    @Override
    public Specification<TestReport> getFilterSpec(GenericFilterField field) {
        Function<Object, Specification<TestReport>> func = filterFunctions.getOrDefault(field.getFieldName(), null);

        if(func == null)
            return null;

        return func.apply(field.getFieldValue());
    }

    @Override
    public Specification<TestReport> getOrderSpec(List<GenericOrder> order) {
        return specAlways();
    }

    public Specification<TestReport> stringFieldLike(String field, String value){
        return (root, query, cb) -> cb.like(cb.lower(root.get((SingularAttribute<? super TestReport, String>) attributes.get(field))), getLikePattern(value));
    }

    public Specification<TestReport> stringFieldEquals(String field, String value){
        return (root, query, cb) -> cb.equal(root.get(attributes.get(field)), getLikePattern(value));
    }

    public static Specification<TestReport> tipoLancamentoIn(Set<TipoLancamentoHora> tipos){
        return (root, query, cb) -> {
            if(tipos == null || tipos.isEmpty())
                return cb.conjunction();

            CriteriaBuilder.In<Object> predicate = cb.in(root.get(TestReport_.lancamentoTipo));
            tipos.forEach(p -> predicate.value(p));
            return predicate;
        };
    }

    private void registerAttributes(){
        attributes.put("id", TestReport_.id);
        attributes.put("empresaRazaoSocial", TestReport_.empresaRazaoSocial);
        attributes.put("empresaCnpj", TestReport_.empresaCnpj);
        attributes.put("funcionarioNome", TestReport_.funcionarioNome);
        attributes.put("funcionarioEmail", TestReport_.funcionarioEmail);
        attributes.put("funcionarioCpf", TestReport_.funcionarioCpf);
        attributes.put("lancamento", TestReport_.lancamento);
        attributes.put("lancamentoLocalizacao", TestReport_.lancamentoLocalizacao);
        attributes.put("lancamentoTipo", TestReport_.lancamentoTipo);
        attributes.put("lancamentoData", TestReport_.lancamentoData);
    }

    private void registerFilterFunctions(){
        filterFunctions.put("empresaRazaoSocial", v -> {
            if(v instanceof String || v == null)
                return stringFieldLike("empresaRazaoSocial", (String)v);
            return specNever();
        });
        filterFunctions.put("funcionarioNome", v -> {
            if(v instanceof String || v == null)
                return stringFieldLike("funcionarioNome", (String)v);
            return specNever();
        });
        filterFunctions.put("empresaCnpj", v -> {
            if(v instanceof String && v != null)
                return stringFieldEquals("empresaCnpj", (String)v);
            return specNever();
        });
        filterFunctions.put("funcionarioCpf", v -> {
            if(v instanceof String && v != null)
                return stringFieldEquals("funcionarioCpf", (String)v);
            return specNever();
        });
        filterFunctions.put("lancamentoTipo", v -> {
            if(v instanceof Set<?> && (((Set)v).isEmpty() || ((Set)v).iterator().next() instanceof TipoLancamentoHora))
                return tipoLancamentoIn((HashSet<TipoLancamentoHora>)v);
            return specNever();
        });
    }
}
