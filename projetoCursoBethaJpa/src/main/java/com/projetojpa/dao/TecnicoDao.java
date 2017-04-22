package com.projetojpa.dao;

import com.projetojpa.modelo.Tecnico;
import com.projetojpa.modelo.TipoTecnico;
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
public class TecnicoDao {

    @PersistenceContext(unitName = "myPUTeste")
    EntityManager entityManager;

    public List<Tecnico> findAllTecnicos() {
        List<Tecnico> tecnico = new ArrayList<>();
        TypedQuery<Tecnico> q = entityManager.createQuery("select t from TECNICO as t", Tecnico.class);
        tecnico = q.getResultList();
        return tecnico;

    }

    public List<Tecnico> findAllTecnicos(int pagina, int limitePagina) {
        int paginaInicial = pagina - 1;
        if (pagina > 1) {
            paginaInicial = (pagina * limitePagina) - limitePagina;
        }
        List<Tecnico> tecnico = new ArrayList<>();
        TypedQuery<Tecnico> q = entityManager.createQuery("select t from TECNICO as t"
                + " order by t.id", 
                Tecnico.class).
                setFirstResult(paginaInicial).
                setMaxResults(limitePagina);
        tecnico = q.getResultList();
        return tecnico;
    }
    
    public List<Tecnico> findAllTecnicos(int pagina, int limitePagina, String pesquisa) {
        int paginaInicial = pagina - 1;
        if (pagina > 1) {
            paginaInicial = (pagina * limitePagina) - limitePagina;
        }
        List<Tecnico> tecnico = new ArrayList<>();
        TypedQuery<Tecnico> q = entityManager.createQuery("select t from "
                + "TECNICO as t where upper(t.nome) like :pesquisa order by t.nome",
                Tecnico.class).
                setFirstResult(paginaInicial).
                setMaxResults(limitePagina);
        q.setParameter("pesquisa", "%" + pesquisa.toUpperCase() + "%");
        tecnico = q.getResultList();
        return tecnico;
    }    
    
    public List<Tecnico> findTecnicosByTipo(String tipo) {
        List<Tecnico> tecnico = new ArrayList<>();
        TypedQuery<Tecnico> q = entityManager.createQuery("select t from "
                + "TECNICO as t where upper(t.tipo) = :tipo order by t.nome",
                Tecnico.class);
        q.setParameter("tipo", TipoTecnico.valueOf(tipo.toUpperCase()));
        tecnico = q.getResultList();
        return tecnico;        
    }

    public Tecnico findTecnico(long id) {
        Tecnico tecnico = entityManager.find(Tecnico.class, id);
        return tecnico;
    }

    public void insert(Tecnico tecnico) {
        entityManager.persist(tecnico);
    }

    public void delete(Long id) {
        entityManager.remove(entityManager.getReference(Tecnico.class, id));
    }

    public Tecnico update(Tecnico tecnico) {
        return entityManager.merge(tecnico);
    }

}
