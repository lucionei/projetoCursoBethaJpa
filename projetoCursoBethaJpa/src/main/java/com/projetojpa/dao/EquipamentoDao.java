package com.projetojpa.dao;

import com.projetojpa.modelo.Equipamento;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author lucionei.chequeto
 */
@Stateless
public class EquipamentoDao {

    @PersistenceContext(unitName = "myPUTeste")
    EntityManager entityManager;

    public List<Equipamento> findAllEquipamentos() {
        List<Equipamento> equipamento = new ArrayList<>();
        TypedQuery<Equipamento> q = entityManager.createQuery("select e from "
                + "EQUIPAMENTO as e order by e.id", Equipamento.class);
        equipamento = q.getResultList();
        return equipamento;
    }

    public List<Equipamento> findAllEquipamentos(int pagina, int limitePagina) {
        int paginaInicial = pagina - 1;
        if (pagina > 1) {
            paginaInicial = (pagina * limitePagina) - limitePagina;
        }
        List<Equipamento> equipamento = new ArrayList<>();
        TypedQuery<Equipamento> q = entityManager.createQuery("select e from "
                + "EQUIPAMENTO as e order by e.id", Equipamento.class).
                setFirstResult(paginaInicial).
                setMaxResults(limitePagina);
        equipamento = q.getResultList();
        return equipamento;
    }

    public List<Equipamento> findAllEquipamentos(int pagina, int limitePagina, String pesquisa) {
        int paginaInicial = pagina - 1;
        if (pagina > 1) {
            paginaInicial = (pagina * limitePagina) - limitePagina;
        }
        List<Equipamento> equipamento = new ArrayList<>();
        TypedQuery<Equipamento> q = entityManager.createQuery("select e from "
                + "EQUIPAMENTO as e where upper(e.descricao) like :pesquisa order by e.id",
                Equipamento.class).
                setFirstResult(paginaInicial).
                setMaxResults(limitePagina);
        q.setParameter("pesquisa", "%" + pesquisa.toUpperCase() + "%");
        equipamento = q.getResultList();
        return equipamento;
    }

    public Equipamento findEquipamento(long id) {

        Equipamento equipamento = entityManager.find(Equipamento.class, id);
        return equipamento;
    }

    public void insert(Equipamento equipamento) {
        entityManager.persist(equipamento);
    }

    public void delete(Long id) {
        entityManager.remove(entityManager.getReference(Equipamento.class, id));
    }

    public Equipamento update(Equipamento equipamento) {
        return entityManager.merge(equipamento);
    }

}
