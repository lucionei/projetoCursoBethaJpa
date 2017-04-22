package com.projetojpa.dao;

import com.projetojpa.modelo.Cliente;
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
public class ClienteDao {

    @PersistenceContext(unitName = "myPUTeste")
    EntityManager entityManager;

    public List<Cliente> findAllClientes() {
        List<Cliente> cliente = new ArrayList<>();
        TypedQuery<Cliente> q = entityManager.createQuery("select c from CLIENTE "
                + "as c", Cliente.class);
        cliente = q.getResultList();
        return cliente;
    }

    public List<Cliente> findAllClientes(int pagina, int limitePagina) {
        int paginaInicial = pagina - 1;
        if (pagina > 1) {
            paginaInicial = (pagina * limitePagina) - limitePagina;
        }
        List<Cliente> cliente = new ArrayList<>();
        TypedQuery<Cliente> q = entityManager.createQuery("select c from "
                + "CLIENTE as c order by c.id", Cliente.class).
                setFirstResult(paginaInicial).setMaxResults(limitePagina);
        cliente = q.getResultList();
        return cliente;
    }

    public List<Cliente> findAllClientes(int pagina, int limitePagina, String pesquisa) {
        int paginaInicial = pagina - 1;
        if (pagina > 1) {
            paginaInicial = (pagina * limitePagina) - limitePagina;
        }
        List<Cliente> cliente = new ArrayList<>();
        TypedQuery<Cliente> q = entityManager.createQuery("select c from "
                + "CLIENTE as c where upper(c.nome) like :pesquisa order by "
                + "c.id", Cliente.class).
                setFirstResult(paginaInicial).setMaxResults(limitePagina);
        q.setParameter("pesquisa", "%" + pesquisa.toUpperCase() + "%");
        cliente = q.getResultList();
        return cliente;
    }
    
    public Cliente findCliente(long id) {
        Cliente cliente = entityManager.find(Cliente.class, id);
        return cliente;
    }

    public void insert(Cliente cliente) {
        entityManager.persist(cliente);
    }

    public void delete(Long id) {
        entityManager.remove(entityManager.getReference(Cliente.class, id));
    }

    public Cliente update(Cliente cliente) {
        return entityManager.merge(cliente);
    }

}
