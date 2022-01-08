package com.soa.services;

import com.google.gson.JsonSyntaxException;
import com.soa.dto.PagedHumanBeingList;
import com.soa.models.HumanBeing;
import com.soa.repository.implementation.CrudRepositoryImplementation;

import javax.persistence.NoResultException;
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
            if (id != null) {
                try {
                    Optional<HumanBeing> humanBeing = repository.findById(id);
                    humanBeingToUpdate.setCreationDate(humanBeing.get().getCreationDate());
                    humanBeingToUpdate.setId(id.longValue());
                    repository.update(humanBeingToUpdate);
                } catch (NumberFormatException e) {
                    throw new BadRequestException("Bad format of id: " + id + ", should be natural number (1,2,...)");
                } catch (NoResultException e) {
                    throw new NotFoundException("No HumanBeing with id " + id);
                }
            }
        }catch (JsonSyntaxException | NotFoundException e){
            throw new NotFoundException("Bad syntax of JSON body");
        }
    }

    public void saveHumanBeing(HumanBeing humanBeingToPersist){
        try {
            repository.save(humanBeingToPersist);
        }catch (JsonSyntaxException | NotFoundException e){
            throw new BadRequestException("Bad syntax of JSON body");
        }
    }

    public void deleteHumanBeing(Integer id) throws IOException {
        try {
            HumanBeing humanBeing = (repository.findById(id)).orElseThrow(() -> new NotFoundException("humanBeing with id = " + id + " does not exist"));
            repository.deleteById(id);
        } catch (NumberFormatException e) {
                throw new BadRequestException("Bad format of id: " + id + ", should be natural number (1,2,...)");

        } catch (NoResultException e) {
                throw new NotFoundException("No HumanBeing with id " + id);
        }
    }

    public PagedHumanBeingList getHumanBeings(String perPage, String curPage, String sortBy, String filterBy) throws IOException {
        PagedHumanBeingList pagedHumanBeingList = repository.findAll(perPage, curPage, sortBy, filterBy);
        return pagedHumanBeingList;
    }

    public HumanBeing getHumanBeing(Integer id){
        try {
            return (repository.findById(id)).orElseThrow(() -> new NotFoundException("humanBeing with id = " + id + " does not exist"));
        }catch (NoResultException e){
            throw new NotFoundException("humanBeing with id = " + id + " does not exist");
        }

    }
}
