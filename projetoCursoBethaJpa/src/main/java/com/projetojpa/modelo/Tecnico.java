package com.projetojpa.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lucionei.chequeto
 */
@XmlRootElement
@Entity(name = "TECNICO")
public class Tecnico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TECNICO")
    private Long id;
    
    @Size(min = 1, max = 150, message = "Informe no mínimo 1 e no máximo 150 caracteres!")
    @NotNull(message = "Campo nome é obrigatório!")
    @Column(name = "NOME")
    private String nome;

    @NotNull(message = "Campo tipo é obrigatório!")
    @Size(min = 1, max = 1, message = "É permitido informar somente T para técnico ou G para gerente no campo tipo.")
    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private TipoTecnico tipo;
    
    @OneToMany(mappedBy = "tecnico")
    private List<ChamadoTecnico> chamadoTecnico;

    @OneToMany(mappedBy = "gerente")
    private List<ChamadoTecnico> chamadoGerente;

    public Tecnico() {
    }

    public Tecnico(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoTecnico getTipo() {
        return tipo;
    }

    public void setTipo(TipoTecnico tipo) {
        this.tipo = tipo;
    }
    
    private List<ChamadoTecnico> getChamadoTecnico() {
        return Collections.unmodifiableList(chamadoTecnico);
    }

    private void setChamadoTecnico(List<ChamadoTecnico> chamadoTecnico) {
        if (chamadoTecnico != null && !chamadoTecnico.isEmpty()) {
            this.chamadoTecnico = new ArrayList<>();
            for (ChamadoTecnico item : chamadoTecnico) {
                item.setTecnico(this);
                this.chamadoTecnico.add(item);
            }
        }
    }   

    private List<ChamadoTecnico> getChamadoGerente() {
        return Collections.unmodifiableList(chamadoTecnico);
    }

    private void setChamadoGerente(List<ChamadoTecnico> chamadoGerente) {
        if (chamadoGerente != null && !chamadoGerente.isEmpty()) {
            this.chamadoGerente = new ArrayList<>();
            for (ChamadoTecnico item : chamadoGerente) {
                item.setGerente(this);
                this.chamadoGerente.add(item);
            }
        }
    }   
    
    @Override
    public String toString() {
        return String.format("{\"id\":\"%s\",\"nome\":\"%s\",\"tipo\":\"%s\"}", id, nome, tipo);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.nome);
        hash = 47 * hash + Objects.hashCode(this.tipo);
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
        final Tecnico other = (Tecnico) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
