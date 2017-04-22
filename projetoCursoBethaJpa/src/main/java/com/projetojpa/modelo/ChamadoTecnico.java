package com.projetojpa.modelo;

import com.projetojpa.util.LocalDateTimeAdapter;
import com.projetojpa.util.Utils;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author lucionei.chequeto
 */
@XmlRootElement
@Entity(name = "CHAMADO_TECNICO")
public class ChamadoTecnico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CHAMADO_TECNICO")
    private Long id;

    @Column(name = "DESCRICAO_PROBLEMA", length = 1000, nullable = false)
    private String descricaoProblema;

    @Column(name = "DESCRICAO_SOLUCAO", length = 1000)
    private String descricaoSolucao;

    @Column(name = "EMISSAO")
    private LocalDateTime emissao;

    @Column(name = "APROVACAO")
    private LocalDateTime aprovacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 1)
    private StatusChamado status;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", nullable = false, length = 1)
    private TipoChamado tipo;

    @ManyToOne()
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ID_TECNICO", nullable = false)
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "ID_GERENTE", nullable = false)
    private Tecnico gerente;

    @ManyToOne
    @JoinColumn(name = "ID_EQUIPAMENTO", nullable = false)
    private Equipamento equipamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public String getDescricaoSolucao() {
        return descricaoSolucao;
    }

    public void setDescricaoSolucao(String descricaoSolucao) {
        this.descricaoSolucao = descricaoSolucao;
    }
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getEmissao() {
        return emissao;
    }
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public void setEmissao(LocalDateTime emissao) {
        this.emissao = emissao;
    }
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getAprovacao() {
        return aprovacao;
    }
    
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public void setAprovacao(LocalDateTime aprovacao) {
        this.aprovacao = aprovacao;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public TipoChamado getTipo() {
        return tipo;
    }

    public void setTipo(TipoChamado tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public Tecnico getGerente() {
        return gerente;
    }

    public void setGerente(Tecnico gerente) {
        this.gerente = gerente;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }


    /*public String validaDados() {
        StringBuilder sb = new StringBuilder();
        if (descricaoProblema.length() > 1000) {
            sb.append("Campo descrição do problema deve possuir no máximo 1000 caracteres\n");
        }

        if (descricaoSolucao.length() > 1000) {
            sb.append("Campo descrição da solução deve possuir no máximo 1000 caracteres\n");
        }

        if (Utils.isEmpty(descricaoProblema)) {
            sb.append("Campo descrição do problema é obrigatório\n");
        }

        if (Utils.isNull(emissao)) {
            sb.append("Campo emissão é obrigatório\n");
        }

        if (emissao.equals(LocalDateTime.MIN)) {
            sb.append("Campo emissão é obrigatório\n");
        }

        if (Utils.isNull(status)) {
            sb.append("Campo status é obrigatório\n");
        }

        if (Utils.isNull(tipo)) {
            sb.append("Campo tipo é obrigatório\n");
        }

        if (Utils.isNull(cliente.getId())) {
            sb.append("Campo cliente é obrigatório\n");
        }

        if (Utils.isNull(tecnico.getId())) {
            sb.append("Campo tecnico é obrigatório\n");
        }

        if (Utils.isNull(gerente.getId())) {
            sb.append("Campo gerente é obrigatório\n");
        }

        if (Utils.isNull(equipamento.getId())) {
            sb.append("Campo equipamento é obrigatório");
        }

        if ((status.equals(StatusChamado.F)) || (aprovacao.isAfter(LocalDateTime.MIN))) {
            if (Utils.isEmpty(descricaoSolucao)) {
                sb.append("Campo descrição da solução deve ser informado");
            }
        }

        return sb.toString();
    }
*/
    @Override
    public String toString() {
        return String.format("{\"id\":\"%s\",\"descricaoProblema\":\"%s\",\"descricaoSolucao\":\"%s\",\"emissao\":\"%s\",\"aprovacao\":\"%s\",\"status\":\"%s\",\"tipo\":\"%s\",\"cliente\":\"%s\",\"tecnico\":\"%s\",\"gerente\":\"%s\",\"equipamento\":\"%s\"}",
                id, descricaoProblema, descricaoSolucao, Utils.nullString(emissao), Utils.nullString(aprovacao == LocalDateTime.MIN ? null : aprovacao), status.toString(), cliente.getId(), tecnico.getId(), gerente.getId(), equipamento.getId());
    }

    /*@Override
    public void parse(Map<String, String> dados) {
        id = dados.get("id") == null || dados.get("id").isEmpty() ? null : Long.parseLong(dados.get("id"));
        descricaoProblema = dados.get("descricaoProblema");
        descricaoSolucao = dados.get("descricaoSolucao");
        emissao = Utils.parseDate(dados.get("emissao"), "dd/MM/yyyy HH:mm:ss");
        aprovacao = Utils.parseDate(dados.get("aprovacao"), "dd/MM/yyyy HH:mm:ss");
        valorTotal = Utils.parseDecimal(dados.get("valorTotal"));
        cliente = new Cliente(Utils.parseLong(dados.get("cliente")));
        tecnico = new Tecnico(Utils.parseLong(dados.get("tecnico")));
        gerente = new Tecnico(Utils.parseLong(dados.get("gerente")));
        equipamento = new Equipamento(Utils.parseLong(dados.get("equipamento")));

        switch (dados.get("status")) {
            case "01":
                status = StatusChamado.A;
                break;
            case "02":
                status = StatusChamado.P;
                break;
            case "03":
                status = StatusChamado.C;
                break;
            case "04":
                status = StatusChamado.F;
                break;
            default:
                status = StatusChamado.A;
                break;
        }

        switch (dados.get("tipo")) {
            case "I":
                tipo = TipoChamado.I;
                break;
            case "E":
                tipo = TipoChamado.E;
                break;
            default:
                tipo = TipoChamado.I;
                break;
        }
    }*/
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.descricaoProblema);
        hash = 89 * hash + Objects.hashCode(this.descricaoSolucao);
        hash = 89 * hash + Objects.hashCode(this.emissao);
        hash = 89 * hash + Objects.hashCode(this.aprovacao);
        hash = 89 * hash + Objects.hashCode(this.status);
        hash = 89 * hash + Objects.hashCode(this.tipo);
        hash = 89 * hash + Objects.hashCode(this.cliente);
        hash = 89 * hash + Objects.hashCode(this.tecnico);
        //hash = 89 * hash + Objects.hashCode(this.gerente);
        hash = 89 * hash + Objects.hashCode(this.equipamento);
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
        final ChamadoTecnico other = (ChamadoTecnico) obj;
        if (!Objects.equals(this.descricaoProblema, other.descricaoProblema)) {
            return false;
        }
        if (!Objects.equals(this.descricaoSolucao, other.descricaoSolucao)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.emissao, other.emissao)) {
            return false;
        }
        if (!Objects.equals(this.aprovacao, other.aprovacao)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (this.tipo != other.tipo) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.tecnico, other.tecnico)) {
            return false;
        }
        if (!Objects.equals(this.gerente, other.gerente)) {
            return false;
        }
        if (!Objects.equals(this.equipamento, other.equipamento)) {
            return false;
        }
        return true;
    }
}
