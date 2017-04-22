package com.projetojpa.resource;

import com.projetojpa.dao.ClienteDao;
import com.projetojpa.modelo.Cliente;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author lucionei.chequeto
 */
@Stateless
@Path("/clientes")
public class ClienteResource {

    @Inject
    ClienteDao clienteDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> findAll() {
        return clienteDao.findAllClientes();
    }

    @GET
    @Path("/paginado/{pagina}/{limitePagina}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> findAll(@PathParam("pagina") int pagina,
            @PathParam("limitePagina") int limitePagina) {
        return clienteDao.findAllClientes(pagina, limitePagina);
    }

    @GET
    @Path("/paginado/{pagina}/{limitePagina}/{pesquisa}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> findAll(@PathParam("pagina") int pagina,
            @PathParam("limitePagina") int limitePagina,
            @PathParam("pesquisa") String pesquisa) {
        return clienteDao.findAllClientes(pagina, limitePagina, pesquisa.toString());
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") long id) {
        final Cliente cliente = clienteDao.findCliente(id);
        if (cliente == null) {
            throw new EntityNotFoundException();
        }
        return Response.ok(cliente).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Cliente insert(Cliente cliente) {
        clienteDao.insert(cliente);
        return cliente;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") Long id, Cliente cliente) {
        if (!Objects.equals(id, cliente.getId())) {
            throw new WebApplicationException();
        }
        clienteDao.delete(id);
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Cliente update(@PathParam("id") Long id, Cliente cliente) {
        if (!Objects.equals(id, cliente.getId())) {
            throw new WebApplicationException();
        }
        return clienteDao.update(cliente);
    }

}
