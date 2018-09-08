package com.drprado.pontointeligente.domain.querySpecifications;

import com.drprado.pontointeligente.domain.entities.Funcionario;
import com.drprado.pontointeligente.domain.entities.Funcionario_;
import com.drprado.pontointeligente.domain.enums.Perfil;
import org.hibernate.query.criteria.internal.predicate.PredicateImplementor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.HashSet;
import java.util.Set;

import static com.drprado.pontointeligente.crosscutting.util.QuerySpecHelper.getLikePattern;

public final class FuncionarioQuery {
    private FuncionarioQuery() {
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
            // o método conjunction sempre traz true, e o disjunction sempre traz false
            if(perfis == null || perfis.isEmpty())
                return cb.conjunction();

            CriteriaBuilder.In<Object> predicate = cb.in(root.get(Funcionario_.perfil));
            perfis.forEach(p -> predicate.value(p));
            return predicate;
        };
    }
}
