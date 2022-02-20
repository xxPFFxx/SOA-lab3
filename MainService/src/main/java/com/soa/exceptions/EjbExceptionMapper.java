package com.soa.exceptions;

import com.soa.dto.ExceptionDTO;

import javax.ejb.EJBException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EjbExceptionMapper implements ExceptionMapper<EJBException> {

    public Response toResponse(EJBException e) {
        Throwable nestedException = e.getCause().getCause().getCause();
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setMessage(nestedException.getMessage());
        if (nestedException instanceof BadRequestException){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(exceptionDTO)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(exceptionDTO)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
