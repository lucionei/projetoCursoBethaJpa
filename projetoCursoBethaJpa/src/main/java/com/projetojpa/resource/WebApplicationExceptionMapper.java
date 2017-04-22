package com.projetojpa.resource;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author lucionei.chequeto
 */
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Registro não encontrado")
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    

}
