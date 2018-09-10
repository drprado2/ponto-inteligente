package com.drprado.pontointeligente.domain.querySpecifications;

import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilterField;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericOrder;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.OrderType;
import com.drprado.pontointeligente.domain.entities.Funcionario;
import com.drprado.pontointeligente.domain.entities.Funcionario_;
import com.drprado.pontointeligente.domain.enums.Perfil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.metamodel.SingularAttribute;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.QuerySpecHelper.getLikePattern;
import static com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.QuerySpecHelper.specNever;

@Component
public final class FuncionarioQuery implements QuerySpecificator<Funcionario> {
    public FuncionarioQuery() {
        filterFunctions = new HashMap<>();
        attributes = new HashMap<>();

        attributes.put("nome", Funcionario_.nome);
        attributes.put("cpf", Funcionario_.cpf);
        attributes.put("email", Funcionario_.email);
        attributes.put("empresaId", Funcionario_.empresaId);
        attributes.put("perfil", Funcionario_.perfil);

        filterFunctions.put("nome", v -> {
            if(v instanceof String || v == null)
                return nameLike((String)v);
            return specNever();
        });
        filterFunctions.put("email", v -> {
            if(v instanceof String || v == null)
                return emailLike((String)v);
            return specNever();
        });
        filterFunctions.put("cpf", v -> {
            if(v instanceof String || v == null)
                return cpfEquals((String)v);
            return specNever();
        });
        filterFunctions.put("empresaId", v -> {
            if(v instanceof Long)
                return empresaEquals((Long)v);
            return specNever();
        });
        filterFunctions.put("perfil", v -> {
            if(v instanceof Set<?> && (((Set)v).isEmpty() || ((Set)v).iterator().next() instanceof Perfil))
                return perfilIn((HashSet<Perfil>)v);
            return specNever();
        });
    }

    private Map<String, Function<Object, Specification<Funcionario>>> filterFunctions;
    public Map<String, SingularAttribute<Funcionario, ? extends Object>> attributes;

    public Specification<Funcionario> getOrderSpec(List<GenericOrder> genericOrders){
        return (root, query, cb) -> {

            List<Order> orders = genericOrders.stream()
                .filter(o -> attributes.getOrDefault(o.getField(), null) != null)
                .map(o -> {
                    SingularAttribute<Funcionario, ?> attribute = attributes.get(o.getField());
                    return o.getOrderType() == OrderType.ASC ? cb.asc(root.get(attribute)) : cb.desc(root.get(attribute));
                })
                .collect(Collectors.toList());

            query.orderBy(orders);

            return cb.conjunction();
        };
    }

    public Specification<Funcionario> getFilterSpec(GenericFilterField field){
        Function<Object, Specification<Funcionario>> func = filterFunctions.getOrDefault(field.getFieldName(), null);

        if(func == null)
            return null;

        return func.apply(field.getFieldValue());
    }

    public static Specification<Funcionario> nameLike(String name){
        return (root, query, cb) -> cb.like(cb.lower(root.get(Funcionario_.nome)), getLikePattern(name));
    }

    public static Specification<Funcionario> emailLike(String email){
        return (root, query, cb) -> cb.like(cb.lower(root.get(Funcionario_.email)), getLikePattern(email));
    }

    public static Specification<Funcionario> cpfEquals(String cpf){
        return (root, query, cb) -> cb.equal(root.get(Funcionario_.cpf), cpf);
    }

    public static Specification<Funcionario> empresaEquals(Long empresaId){
        return (root, query, cb) ->
            empresaId == null ? cb.disjunction() : cb.equal(root.get(Funcionario_.empresaId), empresaId);
    }

    public static Specification<Funcionario> perfilIn(Set<Perfil> perfis){
        return (root, query, cb) -> {
            if(perfis == null || perfis.isEmpty())
                return cb.conjunction();

            CriteriaBuilder.In<Object> predicate = cb.in(root.get(Funcionario_.perfil));
            perfis.forEach(p -> predicate.value(p));
            return predicate;
        };
    }
}
