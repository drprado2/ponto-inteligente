package com.drprado.pontointeligente.domain.entities;

import com.drprado.pontointeligente.domain.enums.Perfil;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Funcionario.class)
public abstract class Funcionario_  {

    public static volatile SingularAttribute<Funcionario, String> nome;
    public static volatile SingularAttribute<Funcionario, String> cpf;
    public static volatile SingularAttribute<Funcionario, String> email;
    public static volatile SingularAttribute<Funcionario, Perfil> perfil;
    public static volatile SingularAttribute<Funcionario, Long> empresaId;
}
