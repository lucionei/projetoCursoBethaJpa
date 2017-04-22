package com.projetojpa.resource;

import com.projetojpa.dao.EquipamentoDao;
import com.projetojpa.modelo.Equipamento;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author lucionei.chequeto
 */
@Stateless
@Path("/equipamentos")
public class EquipamentoResource {

    @Inject
    EquipamentoDao equipamentoDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Equipamento> findAll() {
        return equipamentoDao.findAllEquipamentos();
    }
    
    @GET
    @Path("/paginado/{pagina}/{limitePagina}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Equipamento> findAll(@PathParam("pagina") int pagina,
            @PathParam("limitePagina") int limitePagina) {
        return equipamentoDao.findAllEquipamentos(pagina, limitePagina);
    }    

    @GET
    @Path("/paginado/{pagina}/{limitePagina}/{pesquisa}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Equipamento> findAll(@PathParam("pagina") int pagina,
            @PathParam("limitePagina") int limitePagina, 
            @PathParam("pesquisa") String pesquisa) {
        List<Equipamento> equipamento = equipamentoDao.findAllEquipamentos(pagina, 
                limitePagina, pesquisa.toString());
        return equipamento;
    }        
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") long id) {
        final Equipamento equipamento = equipamentoDao.findEquipamento(id);
        if (equipamento == null) {
            throw new EntityNotFoundException();
        }
        return Response.ok(equipamento).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Equipamento insert(Equipamento equipamento) {
        equipamentoDao.insert(equipamento);
        return equipamento;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") Long id, Equipamento equipamento) {
        if (!Objects.equals(id, equipamento.getId())) {
            throw new WebApplicationException();
        }
        equipamentoDao.delete(id);
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Equipamento update(@PathParam("id") Long id, Equipamento equipamento) {
        if (!Objects.equals(id, equipamento.getId())) {
            throw new WebApplicationException();
        }
        return equipamentoDao.update(equipamento);
    }

}
