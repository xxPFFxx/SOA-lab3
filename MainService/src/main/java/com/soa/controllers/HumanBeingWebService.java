package com.soa.controllers;

import com.soa.dto.HumanBeingDTO;
import com.soa.dto.dtoList.HumanBeingDTOList;
import com.soa.exceptions.MyBadRequestException;
import com.soa.exceptions.MyNotFoundException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface HumanBeingWebService {
    @WebMethod
    HumanBeingDTOList getHumanBeings(@WebParam(name = "perPage") String perPage, @WebParam(name = "curPage") String curPage, @WebParam(name = "sortBy") String sortBy, @WebParam(name = "filterBy") String filterBy) throws MyBadRequestException, MyNotFoundException;
    @WebMethod
    HumanBeingDTO getHumanBeing(@WebParam(name = "id") String id) throws MyBadRequestException, MyNotFoundException;
    @WebMethod
    void createHumanBeing(@WebParam(name = "humanBeing") String humanBeing) throws MyBadRequestException, MyNotFoundException;
    @WebMethod
    void updateHumanBeing(@WebParam(name = "id") String id, @WebParam(name = "humanBeing") String humanBeing) throws MyBadRequestException, MyNotFoundException;
    @WebMethod
    void deleteHumanBeing(@WebParam(name = "id") String id) throws MyBadRequestException, MyNotFoundException;


}
