package com.drprado.pontointeligente.domain.entities;

import com.drprado.pontointeligente.domain.enums.TipoLancamentoHora;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "lancamento")
@AttributeOverride(name = "id", column = @Column(name = "id", nullable = false))
public class Lancamento extends EntidadeBase implements Serializable {

    private static final long serialVersionUID = 6524560251526772839L;

    private LocalDateTime data;
    private String descricao;
    private String localizacao;
    private TipoLancamentoHora tipo;
    private Funcionario funcionario;

    public Lancamento(LocalDateTime data, String descricao, String localizacao, TipoLancamentoHora tipo, Funcionario funcionario) {
        this.data = data;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.tipo = tipo;
        this.funcionario = funcionario;
    }

    protected Lancamento() {
    }

    @Column(name = "data", nullable = false)
    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
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
