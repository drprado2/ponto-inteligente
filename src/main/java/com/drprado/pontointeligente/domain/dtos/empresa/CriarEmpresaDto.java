package com.drprado.pontointeligente.domain.dtos.empresa;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class CriarEmpresaDto {
    private String razaoSocial;
    private String cnpj;
    private String cpf;
    private String email;
    private LocalDateTime dateTime;
    private LocalDate date;
    private LocalTime time;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @NotNull
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime localDate) {
        this.dateTime = localDate;
    }


    @NotBlank(message = "O campo e-mail é de preenchimento obrigatório")
    @Email(message = "O campo deve ser do tipo e-mail")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @CPF(message = "O campo deve ser do tipo CPF")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @NotBlank(message = "Razão social é de preenchimento obrigatório")
    @Length(min = 5, max = 20, message = "A razão social deve ter entre 5 e 20 caracteres")
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @NotBlank(message = "CNPJ é de preenchimento obrigatório")
    @CNPJ(message = "O campo deve ser um CNPJ válido")
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
