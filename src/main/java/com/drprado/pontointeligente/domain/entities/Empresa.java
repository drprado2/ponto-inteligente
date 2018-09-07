package com.drprado.pontointeligente.domain.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresa")
@AttributeOverride(name = "id", column = @Column(name = "id", nullable = false))
public class Empresa extends EntidadeBase implements Serializable {

    private static final long serialVersionUID = 3960436649365666213L;

    private String razaoSocial;
    private String cnpj;
    private List<Funcionario> funcionarios;

    public Empresa(String razaoSocial, String cnpj) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.funcionarios = new ArrayList<>();
    }

    protected Empresa(){
        this.funcionarios = new ArrayList<>();
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

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios.addAll(funcionarios);
    }

    public void setFuncionarios(Funcionario funcionario){
        this.funcionarios.add(funcionario);
    }

    @Override
    public String toString() {
        return "Empresa [id=" + getId() + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + ", dataCriacao=" + getDataCriacao()
                + ", dataAtualizacao=" + getDataAtualizacao() + "]";
    }
}
