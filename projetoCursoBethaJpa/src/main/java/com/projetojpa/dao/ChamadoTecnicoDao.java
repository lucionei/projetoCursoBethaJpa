package com.projetojpa.dao;

import com.projetojpa.modelo.ChamadoTecnico;
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
public class ChamadoTecnicoDao {

    @PersistenceContext(unitName = "myPUTeste")
    EntityManager entityManager;

    public List<ChamadoTecnico> findAllChamados() {
        List<ChamadoTecnico> chamado = new ArrayList<>();
        TypedQuery<ChamadoTecnico> q = entityManager.createQuery("select c from CHAMADO_TECNICO "
                + "as c", ChamadoTecnico.class);
        chamado = q.getResultList();
        return chamado;
    }

    public List<ChamadoTecnico> findAllChamados(int pagina, int limitePagina) {
        int paginaInicial = pagina - 1;
        if (pagina > 1) {
            paginaInicial = (pagina * limitePagina) - limitePagina;
        }
        List<ChamadoTecnico> chamado = new ArrayList<>();
        TypedQuery<ChamadoTecnico> q = entityManager.createQuery("select c from "
                + "CHAMADO_TECNICO as c order by c.id", ChamadoTecnico.class).
                setFirstResult(paginaInicial).setMaxResults(limitePagina);
        chamado = q.getResultList();
        return chamado;
    }

    public List<ChamadoTecnico> findAllChamados(int pagina, int limitePagina, String pesquisa) {
        int paginaInicial = pagina - 1;
        if (pagina > 1) {
            paginaInicial = (pagina * limitePagina) - limitePagina;
        }
        List<ChamadoTecnico> chamado = new ArrayList<>();
        TypedQuery<ChamadoTecnico> q = entityManager.createQuery("select c from "
                + "CHAMADO_TECNICO as c where upper(c.descricaoProblema) like :pesquisa order by "
                + "c.id", ChamadoTecnico.class).
                setFirstResult(paginaInicial).setMaxResults(limitePagina);
        q.setParameter("pesquisa", "%" + pesquisa.toUpperCase() + "%");
        chamado = q.getResultList();
        return chamado;
    }

    public ChamadoTecnico findChamado(long id) {
        ChamadoTecnico chamado = entityManager.find(ChamadoTecnico.class, id);
        return chamado;
    }

    public void insert(ChamadoTecnico chamado) {
        entityManager.persist(chamado);
    }

    public void delete(Long id) {
        entityManager.remove(entityManager.getReference(ChamadoTecnico.class, id));
    }

    public ChamadoTecnico update(ChamadoTecnico chamado) {
        return entityManager.merge(chamado);
    }

}
