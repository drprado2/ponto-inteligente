package com.drprado.pontointeligente.domain.dtos.empresa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ExemploDtoList {
    private List<CriarEmpresaDto> empresas;
    private List<Long> longs;
    private List<LocalDate> datas;
    private List<String> textos;
    private List<Double> doubles;
    private List<Boolean> booleans;
    private List<UUID> guids;
    private List<Integer> ints;

    @NotNull(message = "INTS não podem ser nulos")
    @Size(min = 1, message = "Deve conter ao menos 1 INT")
    public List<Integer> getInts() {
        return ints;
    }

    public void setInts(List<Integer> ints) {
        this.ints = ints;
    }

    public List<UUID> getGuids() {
        return guids;
    }

    public void setGuids(List<UUID> guids) {
        this.guids = guids;
    }

    // O @Valid vai fazer ser validado cada item da lista
    @NotNull(message = "A lista de empresas deve ser preenchida")
    @Size(min = 1, message = "Deve possuir pelo menos 1 empresa")
    @Valid
    public List<CriarEmpresaDto> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<CriarEmpresaDto> empresas) {
        this.empresas = empresas;
    }

    @NotNull(message = "LONGS são de preenchimento obrigatório")
    @Size(min = 1, message = "Deve conter pelo menos 1 LONG")
    public List<Long> getLongs() {
        return longs;
    }

    public void setLongs(List<Long> longs) {
        this.longs = longs;
    }

    @NotNull(message = "Data é de preenchimento obrigatório")
    @Size(min = 1, message = "Deve existir pelo menos 1 data")
    @JsonFormat(pattern = "dd-MM-yyyy")
    public List<LocalDate > getDatas() {
        return datas;
    }

    public void setDatas(List<LocalDate> datas) {
        this.datas = datas;
    }

    public List<String> getTextos() {
        return textos;
    }

    public void setTextos(List<String> textos) {
        this.textos = textos;
    }

    public List<Double> getDoubles() {
        return doubles;
    }

    public void setDoubles(List<Double> doubles) {
        this.doubles = doubles;
    }

    public List<Boolean> getBooleans() {
        return booleans;
    }

    public void setBooleans(List<Boolean> booleans) {
        this.booleans = booleans;
    }
}
