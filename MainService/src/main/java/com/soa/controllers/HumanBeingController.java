package com.soa.controllers;

import com.soa.dto.HumanBeingDTO;
import com.soa.dto.PagedHumanBeingList;
import com.soa.dto.dtoList.HumanBeingDTOList;
import com.soa.mapper.HumanBeingMapper;
import com.soa.models.HumanBeing;
import com.soa.services.HumanBeingService;

import javax.validation.Valid;
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
    public HumanBeingDTO getHumanBeing(@PathParam("id") int id) throws IOException {
        HumanBeing humanBeing = humanBeingService.getHumanBeing(id);
        return humanBeingMapper.mapHumanBeingToHumanBeingDTO(humanBeing);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createHumanBeing(HumanBeingDTO humanBeingDTO) throws IOException {
        HumanBeing humanBeing = humanBeingMapper.mapHumanBeingDTOToHumanBeing(humanBeingDTO);
        humanBeingService.saveHumanBeing(humanBeing);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateHumanBeing(@PathParam("id") int id, @Valid HumanBeingDTO humanBeingDTO) throws IOException {
        HumanBeing humanBeing = humanBeingMapper.mapHumanBeingDTOToHumanBeing(humanBeingDTO);
        humanBeingService.updateHumanBeing(humanBeing, id);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    public void deleteHumanBeing(@PathParam("id") int id) throws IOException {
        humanBeingService.deleteHumanBeing(id);
    }


}
