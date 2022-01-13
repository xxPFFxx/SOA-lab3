package com.soa.services;

import com.google.gson.JsonSyntaxException;
import com.soa.dto.PagedHumanBeingList;
import com.soa.models.HumanBeing;
import com.soa.models.Team;
import com.soa.repository.implementation.CrudRepositoryImplementation;

import javax.persistence.NoResultException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class HumanBeingService {

    private CrudRepositoryImplementation<HumanBeing> humanBeingRepository;
    private CrudRepositoryImplementation<Team> teamRepository;

    public HumanBeingService(){
        humanBeingRepository = new CrudRepositoryImplementation<>(HumanBeing.class);
        teamRepository = new CrudRepositoryImplementation<>(Team.class);
    }

    public void updateHumanBeing(HumanBeing humanBeingToUpdate, Integer id) throws IOException {
        try {
            if (id != null) {
                try {
                    Optional<HumanBeing> humanBeing = humanBeingRepository.findById(id);
                    humanBeingToUpdate.setCreationDate(humanBeing.get().getCreationDate());
                    humanBeingToUpdate.setId(id.longValue());
                    List<Team> teams = teamRepository.findAll();
                    Team teamToUpdate = null;
                    for (Team team : teams){
                        if (team.getName().equals(humanBeingToUpdate.getTeam().getName())){
                            teamToUpdate = team;
                        }
                    }
                    if (teamToUpdate != null){
                        humanBeingToUpdate.setTeam(teamToUpdate);
                    }
                    humanBeingRepository.update(humanBeingToUpdate);
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
            List<Team> teams = teamRepository.findAll();
            Team teamToUpdate = null;
            for (Team team : teams){
                if (team.getName().equals(humanBeingToPersist.getTeam().getName())){
                    teamToUpdate = team;
                }
            }
            if (teamToUpdate != null){
                humanBeingToPersist.setTeam(teamToUpdate);
            }
            humanBeingRepository.save(humanBeingToPersist);
        }catch (JsonSyntaxException | NotFoundException e){
            throw new BadRequestException("Bad syntax of JSON body");
        }
    }

    public void deleteHumanBeing(Integer id) throws IOException {
        try {
            HumanBeing humanBeing = (humanBeingRepository.findById(id)).orElseThrow(() -> new NotFoundException("humanBeing with id = " + id + " does not exist"));
            humanBeingRepository.deleteById(id);
        } catch (NumberFormatException e) {
                throw new BadRequestException("Bad format of id: " + id + ", should be natural number (1,2,...)");

        } catch (NoResultException e) {
                throw new NotFoundException("No HumanBeing with id " + id);
        }
    }

    public PagedHumanBeingList getHumanBeings(String perPage, String curPage, String sortBy, String filterBy) throws IOException {
        PagedHumanBeingList pagedHumanBeingList = humanBeingRepository.findAll(perPage, curPage, sortBy, filterBy);
        return pagedHumanBeingList;
    }

    public HumanBeing getHumanBeing(Integer id){
        try {
            return (humanBeingRepository.findById(id)).orElseThrow(() -> new NotFoundException("humanBeing with id = " + id + " does not exist"));
        }catch (NoResultException e){
            throw new NotFoundException("humanBeing with id = " + id + " does not exist");
        }

    }
}
