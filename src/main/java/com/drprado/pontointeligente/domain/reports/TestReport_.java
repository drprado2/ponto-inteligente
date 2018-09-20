package com.drprado.pontointeligente.domain.reports;

import com.drprado.pontointeligente.domain.enums.TipoLancamentoHora;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import java.util.UUID;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TestReport.class)
public abstract class TestReport_ {

    public static volatile SingularAttribute<TestReport, UUID> id;
    public static volatile SingularAttribute<TestReport, String> empresaRazaoSocial;
    public static volatile SingularAttribute<TestReport, String> empresaCnpj;
    public static volatile SingularAttribute<TestReport, String> funcionarioNome;
    public static volatile SingularAttribute<TestReport, String> funcionarioEmail;
    public static volatile SingularAttribute<TestReport, String> funcionarioCpf;
    public static volatile SingularAttribute<TestReport, String> lancamento;
    public static volatile SingularAttribute<TestReport, String> lancamentoLocalizacao;
    public static volatile SingularAttribute<TestReport, TipoLancamentoHora> lancamentoTipo;
    public static volatile SingularAttribute<TestReport, LocalDateTime> lancamentoData;
}
