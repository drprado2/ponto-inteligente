package com.drprado.pontointeligente.domain.entities;

import com.drprado.pontointeligente.crosscutting.util.ClockFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public class EntidadeBase implements Serializable {

    private Long id;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    // id
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    @Column(name = "DATA_CRIACAO", nullable = false)
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Column(name = "DATA_ATUALIZACAO", nullable = false)
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    // Chamado sempre antes de atualizar
    @PreUpdate
    public void preUpdate() {
        dataAtualizacao = LocalDateTime.now(ClockFactory.get());
    }

    // chamado sempre antes de persistir
    @PrePersist
    public void prePersist() {
        final LocalDateTime atual = LocalDateTime.now(ClockFactory.get());
        dataCriacao = atual;
        dataAtualizacao = atual;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().getTypeName().equals(this.getClass().getTypeName())
                && ((EntidadeBase)obj).id == id;
    }
}
