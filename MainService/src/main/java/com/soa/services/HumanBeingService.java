package com.soa.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.soa.dto.HumanBeingDTO;
import com.soa.dto.PagedHumanBeingList;
import com.soa.mapper.HumanBeingMapper;
import com.soa.models.HumanBeing;
import com.soa.repository.implementation.CrudRepositoryImplementation;
import com.soa.validation.EntityValidator;
import org.postgresql.util.PSQLException;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.util.Optional;

public class HumanBeingService {

    private CrudRepositoryImplementation<HumanBeing> repository;

    public HumanBeingService(){
        repository = new CrudRepositoryImplementation<>(HumanBeing.class);
    }

    public void updateHumanBeing(HumanBeing humanBeingToUpdate, Integer id) throws IOException {
        try {
//            entityValidator.validateHumanBeing(humanBeingToUpdate);
//            entityValidator.validateCoordinates(humanBeingToUpdate.getCoordinates());
//            entityValidator.validateCar(humanBeingToUpdate.getCar());

            if (id != null) {
                try {
                    Optional<HumanBeing> humanBeing = repository.findById(id);
                    humanBeingToUpdate.setCreationDate(humanBeing.get().getCreationDate());
                    humanBeingToUpdate.setId(id.longValue());
                    repository.update(humanBeingToUpdate);
                    // Почему-то ошибки нормально в PUT не обрабатываются, пришлось так сделать
                } catch (NumberFormatException e) {
                    throw new BadRequestException("Bad format of id: " + id + ", should be natural number (1,2,...)");
//                    resp.setStatus(400);
//                    resp.getWriter().println("Bad format of id: " + id + ", should be natural number (1,2,...)");
                } catch (NoResultException e) {
                    throw new NotFoundException("No HumanBeing with id " + id);
//                    resp.setStatus(400);
//                    resp.getWriter().println("No HumanBeing with id " + id);
                }
            }
        }catch (JsonSyntaxException | NotFoundException e){
            throw new NotFoundException("Bad syntax of JSON body");
//            resp.setStatus(400);
//            resp.getWriter().println("Bad syntax of JSON body");
        }
//        catch (BadRequestException e){
//            resp.setStatus(400);
//            resp.getWriter().println(e.getMessage());
//        }

    }

    public void saveHumanBeing(HumanBeing humanBeingToPersist){
        try {
//            entityValidator.validateHumanBeing(humanBeingToPersist);
//            entityValidator.validateCoordinates(humanBeingToPersist.getCoordinates());
//            entityValidator.validateCar(humanBeingToPersist.getCar());
            repository.save(humanBeingToPersist);
        }catch (JsonSyntaxException | NotFoundException e){
            throw new BadRequestException("Bad syntax of JSON body");
        }
    }

    public void deleteHumanBeing(Integer id) throws IOException {
        try {
            HumanBeing humanBeing = (repository.findById(id)).orElseThrow(() -> new NotFoundException("humanBeing with id = " + id + " does not exist"));
            repository.deleteById(id);
            //                  Почему-то ошибки нормально в DELETE не обрабатываются, пришлось так сделать
        } catch (NumberFormatException e) {
                throw new BadRequestException("Bad format of id: " + id + ", should be natural number (1,2,...)");
//            resp.setStatus(400);
//            resp.getWriter().println("Bad format of id: " + humanBeingId + ", should be natural number (1,2,...)");

        } catch (NoResultException e) {
                throw new NotFoundException("No HumanBeing with id " + id);
//            resp.setStatus(404);
//            resp.getWriter().println("No HumanBeing with id " + humanBeingId);
        }
    }

    public PagedHumanBeingList getHumanBeings(String perPage, String curPage, String sortBy, String filterBy) throws IOException {
        PagedHumanBeingList pagedHumanBeingList = repository.findAll(perPage, curPage, sortBy, filterBy);
        return pagedHumanBeingList;
    }

    //TODO на несуществующую сущность кидает 500 ошибку
    public HumanBeing getHumanBeing(Integer id){
        try {
            return (repository.findById(id)).orElseThrow(() -> new NotFoundException("humanBeing with id = " + id + " does not exist"));
        }catch (NoResultException e){
            throw new NotFoundException("humanBeing with id = " + id + " does not exist");
        }

    }
}
