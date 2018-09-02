package com.drprado.pontointeligente.domain.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class EntidadeBase implements Serializable {

    private Long id;
    private Date dataCriacao;
    private Date dataAtualizacao;

    // id
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "DATA_CRIACAO", nullable = false)
    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Column(name = "DATA_ATUALIZACAO", nullable = false)
    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    // Chamado sempre antes de atualizar
    @PreUpdate
    public void preUpdate() {
        dataAtualizacao = new Date();
    }

    // chamado sempre antes de persistir
    @PrePersist
    public void prePersist() {
        final Date atual = new Date();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().getTypeName().equals(this.getClass().getTypeName())
                && ((EntidadeBase)obj).id == id;
    }
}
