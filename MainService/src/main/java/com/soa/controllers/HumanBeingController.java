package com.soa.controllers;

import com.soa.dto.HumanBeingDTO;
import com.soa.dto.PagedHumanBeingList;
import com.soa.dto.dtoList.HumanBeingDTOList;
import com.soa.mapper.HumanBeingMapper;
import com.soa.models.HumanBeing;
import com.soa.services.HumanBeingService;
import com.soa.util.FieldValidationUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("human-beings")
public class HumanBeingController{
    private final HumanBeingService humanBeingService;
    private final HumanBeingMapper humanBeingMapper;

    public HumanBeingController(){
        humanBeingMapper = new HumanBeingMapper();
        humanBeingService = new HumanBeingService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HumanBeingDTOList getHumanBeings(@QueryParam("pageSize") String perPage, @QueryParam("pageNumber") String curPage,
                               @QueryParam("orderBy") String sortBy, @QueryParam("filterBy") String filterBy) throws IOException {
        PagedHumanBeingList pagedHumanBeingList = humanBeingService.getHumanBeings(perPage, curPage, sortBy, filterBy);
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
        HumanBeing humanBeing = humanBeingService.getHumanBeing(long_id);
        return humanBeingMapper.mapHumanBeingToHumanBeingDTO(humanBeing);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createHumanBeing(String humanBeing) throws IOException {
        humanBeingService.saveHumanBeing(humanBeing);
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
        humanBeingService.updateHumanBeing(humanBeing, long_id);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public void deleteHumanBeing(@PathParam("id") String id) throws IOException {
        Long long_id = FieldValidationUtil.getLongFieldValue(id);
        if (long_id == null){
            throw new BadRequestException("id can't be null for PUT request");
        }
        humanBeingService.deleteHumanBeing(long_id);
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
