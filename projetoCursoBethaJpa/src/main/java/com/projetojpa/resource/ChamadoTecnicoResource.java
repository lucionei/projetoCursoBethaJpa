package com.projetojpa.resource;

import com.projetojpa.dao.ChamadoTecnicoDao;
import com.projetojpa.modelo.ChamadoTecnico;
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
@Path("/chamados")
public class ChamadoTecnicoResource {

    @Inject
    ChamadoTecnicoDao chamadoDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ChamadoTecnico> findAll() {
        return chamadoDao.findAllChamados();
    }

    @GET
    @Path("/paginado/{pagina}/{limitePagina}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ChamadoTecnico> findAll(@PathParam("pagina") int pagina,
            @PathParam("limitePagina") int limitePagina) {
        return chamadoDao.findAllChamados(pagina, limitePagina);
    }

    @GET
    @Path("/paginado/{pagina}/{limitePagina}/{pesquisa}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ChamadoTecnico> findAll(@PathParam("pagina") int pagina,
            @PathParam("limitePagina") int limitePagina,
            @PathParam("pesquisa") String pesquisa) {
        return chamadoDao.findAllChamados(pagina, limitePagina, pesquisa.toString());
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") long id) {
        final ChamadoTecnico chamado = chamadoDao.findChamado(id);
        if (chamado == null) {
            throw new EntityNotFoundException();
        }
        return Response.ok(chamado).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ChamadoTecnico insert(ChamadoTecnico chamado) {
        chamadoDao.insert(chamado);
        return chamado;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") Long id, ChamadoTecnico chamado) {
        if (!Objects.equals(id, chamado.getId())) {
            throw new WebApplicationException();
        }
        chamadoDao.delete(id);
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ChamadoTecnico update(@PathParam("id") Long id, ChamadoTecnico chamado) {
        if (!Objects.equals(id, chamado.getId())) {
            throw new WebApplicationException();
        }
        return chamadoDao.update(chamado);
    }

}
