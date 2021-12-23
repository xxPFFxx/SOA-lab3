package com.soa.exceptions;

import com.soa.dto.ExceptionDTO;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundMapper implements ExceptionMapper<NotFoundException> {

    public Response toResponse(NotFoundException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setMessage(e.getMessage());

        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(exceptionDTO)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}