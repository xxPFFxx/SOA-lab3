package com.soa.controllers;

import com.google.common.base.Throwables;
import com.soa.dto.HumanBeingDTO;
import com.soa.dto.PagedHumanBeingList;
import com.soa.dto.dtoList.HumanBeingDTOList;
import com.soa.exceptions.*;
import com.soa.mapper.HumanBeingMapper;
import com.soa.models.HumanBeing;
import com.soa.services.HumanBeingServiceInterface;
import com.soa.util.FieldValidationUtil;
import com.soa.util.RemoteBeanUtil;

import javax.ejb.EJBException;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(endpointInterface = "com.soa.controllers.HumanBeingWebService")
public class HumanBeingWebServiceImpl implements HumanBeingWebService {

    private final HumanBeingServiceInterface humanBeingServiceInterface;
    private final HumanBeingMapper humanBeingMapper;

    public HumanBeingWebServiceImpl(){
        humanBeingMapper = new HumanBeingMapper();
        humanBeingServiceInterface = RemoteBeanUtil.lookupRemoteStatelessBean();
    }
    @WebMethod
    public HumanBeingDTOList getHumanBeings(@WebParam(name = "perPage") String perPage, @WebParam(name = "curPage") String curPage, @WebParam(name = "sortBy") String sortBy, @WebParam(name = "filterBy") String filterBy) throws MyBadRequestException, MyNotFoundException {
         try{
             PagedHumanBeingList pagedHumanBeingList = humanBeingServiceInterface.getHumanBeings(perPage, curPage, sortBy, filterBy);
             return new HumanBeingDTOList((humanBeingMapper.mapHumanBeingListToHumanBeingDTOList(pagedHumanBeingList.getHumanBeingList())), pagedHumanBeingList.getCount());
         }catch (EJBException e){
             ServiceFault fault = new ServiceFault();
             fault.setFaultString(Throwables.getRootCause(e).getMessage());
             System.out.println(Throwables.getRootCause(e).getClass());
             System.out.println(Throwables.getRootCause(e).getMessage());
             System.out.println(e.getClass());
             System.out.println(e.getMessage());
             if (Throwables.getRootCause(e) instanceof NotFoundException){
                 System.out.println("InNotFound");
                 fault.setFaultCode("404");
                 throw new MyNotFoundException(fault);
             }
             else {
                 System.out.println("InBadRequest");
                 fault.setFaultCode("400");
                 throw new MyBadRequestException(fault);
             }
         }catch (Exception e){
             ServiceFault fault = new ServiceFault();
             fault.setFaultString("Error in the request");
             fault.setFaultCode("400");
             throw new MyBadRequestException(fault);
             }
         }


    @WebMethod
    public HumanBeingDTO getHumanBeing(@WebParam(name = "id") String id) throws MyBadRequestException, MyNotFoundException{
        System.out.println(id);
        Long long_id = FieldValidationUtil.getLongFieldValue(id);
        try {
            HumanBeing humanBeing = humanBeingServiceInterface.getHumanBeing(long_id);
            return humanBeingMapper.mapHumanBeingToHumanBeingDTO(humanBeing);
        }catch (EJBException e){
            ServiceFault fault = new ServiceFault();
            fault.setFaultString(Throwables.getRootCause(e).getMessage());
            System.out.println(Throwables.getRootCause(e).getClass());
            System.out.println(Throwables.getRootCause(e).getMessage());
            if (Throwables.getRootCause(e) instanceof NotFoundException){
                fault.setFaultCode("404");
                throw new MyNotFoundException(fault);
            }
            else {
                fault.setFaultCode("400");
                throw new MyBadRequestException(fault);
            }
        }catch (Exception e){
            ServiceFault fault = new ServiceFault();
            fault.setFaultString("Error in the request");
            fault.setFaultCode("400");
            throw new MyBadRequestException(fault);
        }


    }
    @WebMethod
    public void createHumanBeing(@WebParam(name = "humanBeing") String humanBeing) throws MyBadRequestException, MyNotFoundException{
        try {
            humanBeingServiceInterface.saveHumanBeing(humanBeing);
        }catch (EJBException e){
            ServiceFault fault = new ServiceFault();
            fault.setFaultString(Throwables.getRootCause(e).getMessage());
            System.out.println(Throwables.getRootCause(e).getClass());
            System.out.println(Throwables.getRootCause(e).getMessage());
            if (Throwables.getRootCause(e) instanceof NotFoundException){
                fault.setFaultCode("404");
                throw new MyNotFoundException(fault);
            }
            else {
                fault.setFaultCode("400");
                throw new MyBadRequestException(fault);
            }
        }catch (Exception e){
            ServiceFault fault = new ServiceFault();
            fault.setFaultString("Error in the request");
            fault.setFaultCode("400");
            throw new MyBadRequestException(fault);
        }
    }

    @WebMethod
    public void updateHumanBeing(@WebParam(name = "id") String id, @WebParam(name = "humanBeing") String humanBeing) throws MyBadRequestException, MyNotFoundException{
        Long long_id = FieldValidationUtil.getLongFieldValue(id);
        try {
            humanBeingServiceInterface.updateHumanBeing(humanBeing, long_id);
        }catch (EJBException e){
            ServiceFault fault = new ServiceFault();
            fault.setFaultString(Throwables.getRootCause(e).getMessage());
            System.out.println(Throwables.getRootCause(e).getClass());
            System.out.println(Throwables.getRootCause(e).getMessage());
            if (Throwables.getRootCause(e) instanceof NotFoundException){
                fault.setFaultCode("404");
                throw new MyNotFoundException(fault);
            }
            else {
                fault.setFaultCode("400");
                throw new MyBadRequestException(fault);
            }
        }catch (Exception e){
            ServiceFault fault = new ServiceFault();
            fault.setFaultString("Error in the request");
            fault.setFaultCode("400");
            throw new MyBadRequestException(fault);
        }
    }

    @WebMethod
    public void deleteHumanBeing(@WebParam(name = "id") String id) throws MyBadRequestException, MyNotFoundException {
        Long long_id = FieldValidationUtil.getLongFieldValue(id);
        try {
            humanBeingServiceInterface.deleteHumanBeing(long_id);
        }catch (EJBException e){
            ServiceFault fault = new ServiceFault();
            fault.setFaultString(Throwables.getRootCause(e).getMessage());
            System.out.println(Throwables.getRootCause(e).getClass());
            System.out.println(Throwables.getRootCause(e).getMessage());
            if (Throwables.getRootCause(e) instanceof NotFoundException){
                fault.setFaultCode("404");
                throw new MyNotFoundException(fault);
            }
            else {
                fault.setFaultCode("400");
                throw new MyBadRequestException(fault);
            }
        }catch (Exception e){
            ServiceFault fault = new ServiceFault();
            fault.setFaultString("Error in the request");
            fault.setFaultCode("400");
            throw new MyBadRequestException(fault);
        }

    }

}
