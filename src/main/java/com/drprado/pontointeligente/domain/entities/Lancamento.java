package com.drprado.pontointeligente.domain.entities;

import com.drprado.pontointeligente.domain.enums.TipoLancamentoHora;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "lancamento")
public class Lancamento extends EntidadeBase implements Serializable {

    private static final long serialVersionUID = 6524560251526772839L;

    private Date data;
    private String descricao;
    private String localizacao;
    private TipoLancamentoHora tipo;
    private Funcionario funcionario;

    public Lancamento() {
    }

    // timestamp será gravado data e hora
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data", nullable = false)
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Column(name = "descricao", nullable = true)
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Column(name = "localizacao", nullable = true)
    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    public TipoLancamentoHora getTipo() {
        return tipo;
    }

    public void setTipo(TipoLancamentoHora tipo) {
        this.tipo = tipo;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public String toString() {
        return "Lancamento [id=" + getId() + ", data=" + data + ", descricao=" + descricao + ", localizacao=" + localizacao
                + ", dataCriacao=" + getDataCriacao() + ", dataAtualizacao=" + getDataAtualizacao() + ", tipo=" + tipo
                + ", funcionario=" + funcionario + "]";
    }

}
