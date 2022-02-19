package com.soa.controllers;

import com.soa.dto.HumanBeingDTO;
import com.soa.dto.PagedHumanBeingList;
import com.soa.dto.dtoList.HumanBeingDTOList;
import com.soa.mapper.HumanBeingMapper;
import com.soa.models.HumanBeing;
import com.soa.services.HumanBeingService;
import com.soa.services.HumanBeingServiceInterface;
import com.soa.util.FieldValidationUtil;
import com.soa.util.RemoteBeanUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("human-beings")
public class HumanBeingController{
    private final HumanBeingServiceInterface humanBeingServiceInterface;
    private final HumanBeingMapper humanBeingMapper;

    public HumanBeingController(){
        humanBeingMapper = new HumanBeingMapper();
        humanBeingServiceInterface = RemoteBeanUtil.lookupRemoteStatelessBean();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HumanBeingDTOList getHumanBeings(@QueryParam("pageSize") String perPage, @QueryParam("pageNumber") String curPage,
                               @QueryParam("orderBy") String sortBy, @QueryParam("filterBy") String filterBy) throws IOException {
        System.out.println("Hi dude");
        PagedHumanBeingList pagedHumanBeingList = humanBeingServiceInterface.getHumanBeings(perPage, curPage, sortBy, filterBy);
        return new HumanBeingDTOList((humanBeingMapper.mapHumanBeingListToHumanBeingDTOList(pagedHumanBeingList.getHumanBeingList())), pagedHumanBeingList.getCount());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public HumanBeingDTO getHumanBeing(@PathParam("id") String id) throws IOException {
        Long long_id = FieldValidationUtil.getLongFieldValue(id);
        if (long_id == null){
            throw new BadRequestException("id can't be null for PUT request");
        }
        HumanBeing humanBeing = humanBeingServiceInterface.getHumanBeing(long_id);
        return humanBeingMapper.mapHumanBeingToHumanBeingDTO(humanBeing);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createHumanBeing(String humanBeing) throws IOException {
        humanBeingServiceInterface.saveHumanBeing(humanBeing);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateHumanBeing(@PathParam("id") String id, String humanBeing) throws IOException {
        Long long_id = FieldValidationUtil.getLongFieldValue(id);
        if (long_id == null){
            throw new BadRequestException("id can't be null for PUT request");
        }
        humanBeingServiceInterface.updateHumanBeing(humanBeing, long_id);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public void deleteHumanBeing(@PathParam("id") String id) throws IOException {
        Long long_id = FieldValidationUtil.getLongFieldValue(id);
        if (long_id == null){
            throw new BadRequestException("id can't be null for PUT request");
        }
        humanBeingServiceInterface.deleteHumanBeing(long_id);
    }

    @OPTIONS
    public Response options(){
        return Response.ok().build();
    }

    @OPTIONS
    @Path("{id}")
    public Response optionsWithId(){
        return Response.ok().build();
    }

}
