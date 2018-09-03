package com.drprado.pontointeligente.domain.entities;

import com.drprado.pontointeligente.domain.enums.Perfil;
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "funcionario")
@AttributeOverride(name = "id", column = @Column(name = "id", nullable = false))
public class Funcionario extends EntidadeBase implements Serializable {

    private static final long serialVersionUID = -5754246207015712518L;

    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private BigDecimal valorHora;
    private Float qtdHorasTrabalhoDia;
    private Float qtdHorasAlmoco;
    private Perfil perfil;
    private Empresa empresa;
    private List<Lancamento> lancamentos;

    public Funcionario(String nome, String email, String senha, String cpf) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        lancamentos = new ArrayList<>();
        perfil = Perfil.ROLE_USUARIO;
    }

    protected Funcionario(){
        lancamentos = new ArrayList<>();
    }

    @Column(name = "nome", nullable = false)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "cpf", nullable = false)
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Column(name = "valor_hora", nullable = true)
    public BigDecimal getValorHora() {
        return valorHora;
    }

    // Esse transient é para o JPA ignorar esse cara no mapeamento
    @Transient
    public Optional<BigDecimal> getValorHoraOpt() {
        return Optional.ofNullable(valorHora);
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    @Column(name = "qtd_horas_trabalho_dia", nullable = true)
    public Float getQtdHorasTrabalhoDia() {
        return qtdHorasTrabalhoDia;
    }

    @Transient
    public Optional<Float> getQtdHorasTrabalhoDiaOpt() {
        return Optional.ofNullable(qtdHorasTrabalhoDia);
    }

    public void setQtdHorasTrabalhoDia(Float qtdHorasTrabalhoDia) {
        this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
    }

    @Column(name = "qtd_horas_almoco", nullable = true)
    public Float getQtdHorasAlmoco() {
        return qtdHorasAlmoco;
    }

    @Transient
    public Optional<Float> getQtdHorasAlmocoOpt() {
        return Optional.ofNullable(qtdHorasAlmoco);
    }

    public void setQtdHorasAlmoco(Float qtdHorasAlmoco) {
        this.qtdHorasAlmoco = qtdHorasAlmoco;
    }

    // Essa notation faz com que seja guardado a string do enum no banco e nao so o numero
    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }


    @Column(name = "senha", nullable = false)
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // Muitos de mim para um dele, ou seja muitos funcionarios para 1 empresa
    // ou seja id da empresa do funcionario
    // o fetch do tipo EAGER já trara a empresa carregada qnd trazer o funcionario do banco
    @ManyToOne(fetch = FetchType.EAGER)
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    // um de mim para muitos dele, 1 funcionario muitos lancamentos
    // ou seja id do lancamento no funcionario
    // O fetch LAZY fará um Proxy de tal maneira que só serão carregados os lancamentos
    // qnd solicitar seu valor
    // O cascade ferá com q qnd for deletado um funcionario seus lancamentos tmb sejam deletados
    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos.addAll(lancamentos);
    }

    public void setLancamentos(Lancamento lancamento) {
        this.lancamentos.add(lancamento);
    }

    @Override
    public String toString() {
        return "Funcionario [id=" + getId() + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf
                + ", valorHora=" + valorHora + ", qtdHorasTrabalhoDia=" + qtdHorasTrabalhoDia + ", qtdHorasAlmoco="
                + qtdHorasAlmoco + ", perfil=" + perfil + ", dataCriacao="
                + getDataCriacao() + ", dataAtualizacao=" + getDataAtualizacao() + ", empresa=" + empresa + "]";
    }
}
