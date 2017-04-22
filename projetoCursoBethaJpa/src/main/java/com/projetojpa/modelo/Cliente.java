package com.projetojpa.modelo;

import com.projetojpa.util.Utils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity(name = "CLIENTE")
public class Cliente implements Serializable {

    private static final int[] PESOCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESOCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENTE")
    private Long id;

    @NotNull
    @Column(name = "NOME", length = 200)
    private String nome;

    @NotNull
    @Column(name = "TELEFONE", length = 20)
    private String telefone;

    @NotNull
    @Column(name = "DOCUMENTO", length = 20)
    private String documento;
    
    @Column(name = "EMAIL", length = 120)
    private String email;

    @OneToMany(mappedBy = "cliente")
    private List<ChamadoTecnico> chamadoTecnico;

    public Cliente() {
    }

    public Cliente(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    private List<ChamadoTecnico> getChamadoTecnico() {
        return Collections.unmodifiableList(chamadoTecnico);
    }

    private void setChamadoTecnico(List<ChamadoTecnico> chamadoTecnico) {
        if (chamadoTecnico != null && !chamadoTecnico.isEmpty()) {
            this.chamadoTecnico = new ArrayList<>();
            for (ChamadoTecnico item : chamadoTecnico) {
                item.setCliente(this);
                this.chamadoTecnico.add(item);
            }
        }
    }

    public String validaDados() {
        StringBuilder sb = new StringBuilder();

        if (!Utils.isEmpty(email)) {
            if (!email.matches("\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b")) {
                sb.append("Informe um e-mail válido\n");
            }
        }
        if (!isValidCPF(documento)) {
            if (!isValidCNPJ(documento)) {
                sb.append("Informe um documento válido\n");
            }
        }
        return sb.toString();
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    private static boolean isValidCPF(String cpf) {
        if ((cpf == null) || (cpf.length() != 11)) {
            return false;
        }

        Integer digito1 = calcularDigito(cpf.substring(0, 9), PESOCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, PESOCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    private static boolean isValidCNPJ(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14)) {
            return false;
        }

        Integer digito1 = calcularDigito(cnpj.substring(0, 12), PESOCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, PESOCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }

    @Override
    public String toString() {
        return String.format("{\"id\":\"%s\",\"nome\":\"%s\",\"telefone\":\"%s\",\"documento\":\"%s\",\"email\":\"%s\"}", id, nome, telefone, documento, email);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.nome);
        hash = 17 * hash + Objects.hashCode(this.telefone);
        hash = 17 * hash + Objects.hashCode(this.documento);
        hash = 17 * hash + Objects.hashCode(this.email);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.telefone, other.telefone)) {
            return false;
        }
        if (!Objects.equals(this.documento, other.documento)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
