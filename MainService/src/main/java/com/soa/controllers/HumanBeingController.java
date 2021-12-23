package com.soa.controllers;

import com.soa.dto.PagedHumanBeingList;
import com.soa.dto.dtoList.HumanBeingDTOList;
import com.soa.mapper.HumanBeingMapper;
import com.soa.services.HumanBeingService;

import javax.ws.rs.*;
import java.io.IOException;

@Path("human-beings")
public class HumanBeingController{
    private HumanBeingService humanBeingService;
    private HumanBeingMapper humanBeingMapper;

    public HumanBeingController(){
        humanBeingMapper = new HumanBeingMapper();
        humanBeingService = new HumanBeingService();
    }

    @GET
    public HumanBeingDTOList getHumanBeings(@QueryParam("pageSize") String perPage, @QueryParam("pageNumber") String curPage,
                               @QueryParam("orderBy") String sortBy, @QueryParam("filterBy") String filterBy) throws IOException {
        PagedHumanBeingList pagedHumanBeingList = humanBeingService.getHumanBeings(perPage, curPage, sortBy, filterBy);
        return new HumanBeingDTOList((humanBeingMapper.mapHumanBeingListToHumanBeingDTOList(pagedHumanBeingList.getHumanBeingList())), pagedHumanBeingList.getCount());
    }

//    @POST
//    public void createHumanBeing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        cors(response);
//        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        humanBeingService.saveHumanBeing(requestBody);
//    }
//
//    @PUT
//    public void updateHumanBeing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        cors(response);
//        String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        String pathInfo = request.getPathInfo();
//        humanBeingService.updateHumanBeing(requestBody, pathInfo, response);
//    }
//    @DELETE
//    public void deleteHumanBeing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        cors(response);
//        String pathInfo = request.getPathInfo();
//        humanBeingService.deleteHumanBeing(pathInfo, response);
//    }
//    @OPTIONS
//    public void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        cors(response);
//    }

}
