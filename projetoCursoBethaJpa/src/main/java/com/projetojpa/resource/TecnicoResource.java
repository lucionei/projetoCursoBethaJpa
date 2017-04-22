package com.projetojpa.resource;

import com.projetojpa.dao.TecnicoDao;
import com.projetojpa.modelo.Tecnico;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author lucionei.chequeto
 */
@Stateless
@Path("/tecnicos")
public class TecnicoResource {

    @Inject
    TecnicoDao tecnicoDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tecnico> findAll() {
        return tecnicoDao.findAllTecnicos();
    }

    @GET
    @Path("/paginado/{pagina}/{limitePagina}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tecnico> findAll(@PathParam("pagina") int pagina, 
            @PathParam("limitePagina") int limitePagina) {
        return tecnicoDao.findAllTecnicos(pagina, limitePagina);
    }

    @GET
    @Path("/paginado/{pagina}/{limitePagina}/{pesquisa}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tecnico> findAll(@PathParam("pagina") int pagina,
            @PathParam("limitePagina") int limitePagina,
            @PathParam("pesquisa") String pesquisa) {
        return tecnicoDao.findAllTecnicos(pagina, limitePagina, pesquisa);
    }

    @GET
    @Path("/tipo/{tipo}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tecnico> findAllByTipo(@PathParam("tipo") String tipo) {
        return tecnicoDao.findTecnicosByTipo(tipo);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") long id) {
        final Tecnico tecnico = tecnicoDao.findTecnico(id);
        if (tecnico == null) {
            throw new EntityNotFoundException();
        }
        return Response.ok(tecnico).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Tecnico insert(Tecnico tecnico) {
        tecnicoDao.insert(tecnico);
        return tecnico;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") Long id, Tecnico tecnico) {
        if (!Objects.equals(id, tecnico.getId())) {
            throw new WebApplicationException();
        }
        tecnicoDao.delete(id);
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Tecnico update(@PathParam("id") Long id, Tecnico tecnico) {
        if (!Objects.equals(id, tecnico.getId())) {
            throw new WebApplicationException();
        }
        return tecnicoDao.update(tecnico);
    }

}
