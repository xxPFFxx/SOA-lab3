package com.soa.exceptions;

import com.soa.dto.ExceptionDTO;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestMapper implements ExceptionMapper<BadRequestException> {

    public Response toResponse(BadRequestException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setMessage(e.getMessage());

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(exceptionDTO)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}