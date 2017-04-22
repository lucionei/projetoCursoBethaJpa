package com.projetojpa.modelo;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lucionei.chequeto
 */
@XmlRootElement
@Entity(name = "EQUIPAMENTO")
public class Equipamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EQUIPAMENTO")
    private Long id;

    @Size(min = 1, max = 150, message = "Informe no mínimo 1 e no máximo 150 caracteres!")
    @NotNull(message = "Campo descrição é obrigatório!")
    @Column(name = "DESCRICAO", length = 150)
    private String descricao;

    @OneToMany(mappedBy = "equipamento")
    private List<ChamadoTecnico> chamadoTecnico;

    public Equipamento() {
    }

    public Equipamento(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    private List<ChamadoTecnico> getChamadoTecnico() {
        return Collections.unmodifiableList(chamadoTecnico);
    }

    private void setChamadoTecnico(List<ChamadoTecnico> chamadoTecnico) {
        if (chamadoTecnico != null && !chamadoTecnico.isEmpty()) {
            this.chamadoTecnico = new ArrayList<>();
            for (ChamadoTecnico item : chamadoTecnico) {
                item.setEquipamento(this);
                this.chamadoTecnico.add(item);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("{\"id\":\"%s\",\"descricao\":\"%s\"}", id, descricao);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.descricao);
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
        final Equipamento other = (Equipamento) obj;
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
