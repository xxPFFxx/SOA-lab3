package com.soa.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.soa.dao.HumanBeingDAO;
import com.soa.dto.HumanBeingDTO;
import com.soa.dto.PagedHumanBeingList;
import com.soa.mapper.HumanBeingMapper;
import com.soa.models.HumanBeing;

import javax.persistence.NoResultException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

public class HumanBeingService {

    private final HumanBeingDAO humanBeingDAO;
    private final Gson gson;
    private final HumanBeingMapper humanBeingMapper;

    public HumanBeingService(){
        humanBeingDAO = new HumanBeingDAO();
        gson = new GsonBuilder().setPrettyPrinting().create();
        humanBeingMapper = new HumanBeingMapper();
    }

    public void updateHumanBeing(String humanBeingRequest, Long id) throws IOException {
        try {
            if (id != null) {
                try {
                    HumanBeingDTO humanBeingDTO = gson.fromJson(humanBeingRequest, HumanBeingDTO.class);
                    HumanBeing humanBeingToUpdate = humanBeingMapper.mapHumanBeingDTOToHumanBeing(humanBeingDTO);
                    Optional<HumanBeing> humanBeing = humanBeingDAO.getHumanBeing(id);
                    humanBeingToUpdate.setCreationDate(humanBeing.get().getCreationDate());
                    humanBeingToUpdate.setId(id.longValue());
                    humanBeingDAO.updateHumanBeing(humanBeingToUpdate);
                } catch (NumberFormatException e) {
                    throw new BadRequestException("Bad format of id: " + id + ", should be natural number (1,2,...)");
                } catch (NoResultException | NoSuchElementException e) {
                    throw new NotFoundException("No HumanBeing with id " + id);
                }
            }
        }catch (JsonSyntaxException e){
            throw new BadRequestException("Bad syntax of JSON body");
        }
    }

    public void saveHumanBeing(String humanBeing){
        try {
            HumanBeingDTO humanBeingDTO = gson.fromJson(humanBeing, HumanBeingDTO.class);
            HumanBeing humanBeingToPersist = humanBeingMapper.mapHumanBeingDTOToHumanBeing(humanBeingDTO);
            humanBeingDAO.createHumanBeing(humanBeingToPersist);
        }catch (JsonSyntaxException e){
            throw new BadRequestException("Bad syntax of JSON body");
        }
    }

    public void deleteHumanBeing(Long id) throws IOException {
        try {
            HumanBeing humanBeing = (humanBeingDAO.getHumanBeing(id)).orElseThrow(() -> new NotFoundException("humanBeing with id = " + id + " does not exist"));
            humanBeingDAO.deleteHumanBeing(id);
        } catch (NumberFormatException e) {
                throw new BadRequestException("Bad format of id: " + id + ", should be natural number (1,2,...)");

        } catch (NoResultException e) {
                throw new NotFoundException("No HumanBeing with id " + id);
        }
    }

    public PagedHumanBeingList getHumanBeings(String perPage, String curPage, String sortBy, String filterBy) throws IOException {
        PagedHumanBeingList pagedHumanBeingList = humanBeingDAO.findAll(perPage, curPage, sortBy, filterBy);
        return pagedHumanBeingList;
    }

    public HumanBeing getHumanBeing(Long id){
        try {
            return (humanBeingDAO.getHumanBeing(id)).orElseThrow(() -> new NotFoundException("humanBeing with id = " + id + " does not exist"));
        }catch (NoResultException e){
            throw new NotFoundException("humanBeing with id = " + id + " does not exist");
        }

    }
}
