package com.drprado.pontointeligente.domain.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "empresa")
public class Empresa extends EntidadeBase implements Serializable {

    private static final long serialVersionUID = 3960436649365666213L;

    private String razaoSocial;
    private String cnpj;
    private List<Funcionario> funcionarios;

    public Empresa() {
    }

    @Column(name = "razao_social", nullable = false)
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @Column(name = "cnpj", nullable = false)
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    @Override
    public String toString() {
        return "Empresa [id=" + getId() + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + ", dataCriacao=" + getDataCriacao()
                + ", dataAtualizacao=" + getDataAtualizacao() + "]";
    }
}
