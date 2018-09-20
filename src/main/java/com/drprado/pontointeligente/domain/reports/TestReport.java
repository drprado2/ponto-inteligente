package com.drprado.pontointeligente.domain.reports;

import com.drprado.pontointeligente.domain.enums.TipoLancamentoHora;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Immutable
@Subselect(
        "SELECT UUID() as id," +
        "       em.razao_social empresaRazaoSocial," +
        "       em.cnpj as empresaCnpj," +
        "       fu.nome as funcionarioNome," +
        "       fu.email as funcionarioEmail," +
        "       fu.perfil as fucionarioPerfil," +
        "       fu.cpf as funcionarioCpf," +
        "       la.descricao as lancamento," +
        "       la.localizacao as lancamentoLocalizacao," +
        "       la.tipo as lancamentoTipo," +
        "       la.data as lancamentoData" +
        "  FROM empresa as em" +
        "  LEFT JOIN funcionario as fu on (em.id = fu.empresa_id)" +
        "  LEFT JOIN lancamento as la on (fu.id = la.funcionario_id)" +
        "  ORDER BY lancamentoData desc, empresaRazaoSocial asc, funcionarioNome asc"
)
public class TestReport {
    private UUID id;
    private String empresaRazaoSocial;
    private String empresaCnpj;
    private String funcionarioNome;
    private String funcionarioEmail;
    private String funcionarioCpf;
    private String lancamento;
    private String lancamentoLocalizacao;
    private TipoLancamentoHora lancamentoTipo;
    private LocalDateTime lancamentoData;

    @Id
    @Column(name = "id")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Column(name = "empresaRazaoSocial")
    public String getEmpresaRazaoSocial() {
        return empresaRazaoSocial;
    }

    public void setEmpresaRazaoSocial(String empresaRazaoSocial) {
        this.empresaRazaoSocial = empresaRazaoSocial;
    }

    @Column(name = "empresaCnpj")
    public String getEmpresaCnpj() {
        return empresaCnpj;
    }

    public void setEmpresaCnpj(String empresaCnpj) {
        this.empresaCnpj = empresaCnpj;
    }

    @Column(name = "funcionarioNome")
    public String getFuncionarioNome() {
        return funcionarioNome;
    }

    public void setFuncionarioNome(String funcionarioNome) {
        this.funcionarioNome = funcionarioNome;
    }

    @Column(name = "funcionarioEmail")
    public String getFuncionarioEmail() {
        return funcionarioEmail;
    }

    public void setFuncionarioEmail(String funcionarioEmail) {
        this.funcionarioEmail = funcionarioEmail;
    }

    @Column(name = "funcionarioCpf")
    public String getFuncionarioCpf() {
        return funcionarioCpf;
    }

    public void setFuncionarioCpf(String funcionarioCpf) {
        this.funcionarioCpf = funcionarioCpf;
    }

    @Column(name = "lancamento")
    public String getLancamento() {
        return lancamento;
    }

    public void setLancamento(String lancamento) {
        this.lancamento = lancamento;
    }

    @Column(name = "lancamentoLocalizacao")
    public String getLancamentoLocalizacao() {
        return lancamentoLocalizacao;
    }

    public void setLancamentoLocalizacao(String lancamentoLocalizacao) {
        this.lancamentoLocalizacao = lancamentoLocalizacao;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "lancamentoTipo")
    public TipoLancamentoHora getLancamentoTipo() {
        return lancamentoTipo;
    }

    public void setLancamentoTipo(TipoLancamentoHora lancamentoTipo) {
        this.lancamentoTipo = lancamentoTipo;
    }

    @Column(name = "lancamentoData")
    public LocalDateTime getLancamentoData() {
        return lancamentoData;
    }

    public void setLancamentoData(LocalDateTime lancamentoData) {
        this.lancamentoData = lancamentoData;
    }
}
