package com.soa.exceptions;

import org.glassfish.jersey.server.ParamException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ParamExceptionMapper implements ExceptionMapper<ParamException> {
    @Override
    public Response toResponse(ParamException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getParameterName() + " incorrect type").build();
    }
}