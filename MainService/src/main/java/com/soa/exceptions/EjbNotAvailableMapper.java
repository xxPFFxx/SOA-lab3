package com.soa.exceptions;

import com.soa.dto.ExceptionDTO;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class EjbNotAvailableMapper implements ExceptionMapper<EjbNotAvailableException> {

    public Response toResponse(EjbNotAvailableException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setMessage(e.getMessage());

        return Response
                .status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(exceptionDTO)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
