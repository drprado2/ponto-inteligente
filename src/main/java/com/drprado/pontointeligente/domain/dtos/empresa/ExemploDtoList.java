package com.drprado.pontointeligente.domain.dtos.empresa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class ExemploDtoList {
    private List<CriarEmpresaDto> empresas;
    private List<Long> longs;
    private List<LocalDate> datas;
    private List<LocalTime> tempos;
    private List<LocalDateTime> dataTempo;
    private List<String> textos;
    private List<Double> doubles;
    private List<Boolean> booleans;
    private List<UUID> guids;
    private List<Integer> ints;

    @NotNull(message = "Tempos é de preenchimento obrigatório")
    @Size(min = 1, message = "Deve ter pelo menos 1 tempo")
    @JsonFormat(pattern = "HH:mm:ss")
    public List<LocalTime> getTempos() {
        return tempos;
    }

    public void setTempos(List<LocalTime> tempos) {
        this.tempos = tempos;
    }

    @NotNull(message = "DataTempo deve ser preenchida")
    @Size(min = 1, message = "Deve ter pelo menos 1 DataTempo")
    public List<LocalDateTime> getDataTempo() {
        return dataTempo;
    }

    public void setDataTempo(List<LocalDateTime> dataTempo) {
        this.dataTempo = dataTempo;
    }

    @NotNull(message = "INTS não podem ser nulos")
    @Size(min = 1, message = "Deve conter ao menos 1 INT")
    public List<@NotNull @Valid @Negative Integer> getInts() {
        return ints;
    }

    public void setInts(List<Integer> ints) {
        this.ints = ints;
    }

    @NotNull(message = "Guids devem ser preenchidos")
    @Size(min = 1, message = "Guids devem ser preenchidos")
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

    @NotNull(message = "Textos devem ser preenchidos")
    @Size(message = "Textos devem ser preenchidos", min = 1)
    public List<
            @Valid
            @NotBlank(message = "O texto dos textos não pode ser nulo")
            @Length(min = 5, max = 20, message = "O comprimento dos textos deve estar entre 5 e 20") String> getTextos() {
        return textos;
    }

    public void setTextos(List<String> textos) {
        this.textos = textos;
    }

    @NotNull(message = "Doubles deve ser preenchido")
    @Size(min = 1, message = "Doubles deve ser preenchido")
    public List<
            @Valid
            @PositiveOrZero(message = "Cada double de doubles deve ser positivo ou zero") Double> getDoubles() {
        return doubles;
    }

    public void setDoubles(List<Double> doubles) {
        this.doubles = doubles;
    }

    @NotNull(message = "O campo Booleans deve ser preenchido")
    @Size(message = "O campo Booleans deve ser preenchido")
    public List<@AssertTrue @Valid Boolean> getBooleans() {
        return booleans;
    }

    public void setBooleans(List<Boolean> booleans) {
        this.booleans = booleans;
    }
}
