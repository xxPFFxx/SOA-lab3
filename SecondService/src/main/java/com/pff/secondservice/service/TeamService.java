package com.pff.secondservice.service;

import com.pff.secondservice.client.RestClient;
import com.pff.secondservice.dto.HumanBeingDTO;
import com.pff.secondservice.dto.dtoList.HumanBeingDTOList;
import com.pff.secondservice.enums.Mood;
import com.pff.secondservice.exception.BadRequestException;
import com.pff.secondservice.exception.NotFoundException;
import com.pff.secondservice.models.Team;
import com.pff.secondservice.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

@Service
public class TeamService {

    private final RestClient restClient;
    private final TeamRepository teamRepository;

    public TeamService(RestClient restClient, TeamRepository teamRepository) {
        this.restClient = restClient;
        this.teamRepository = teamRepository;
    }

    public void removeWithoutToothpick(Integer teamId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        HumanBeingDTOList humanBeingsInTeam = restClient.getHumanBeingsByTeamId(teamId);
        List<HumanBeingDTO> humanBeingDTOList = humanBeingsInTeam.getHumanBeingList();
        if (humanBeingDTOList.isEmpty()){
            throw new BadRequestException("No team with teamId " + teamId + " or this team has zero members");
        }
        for (HumanBeingDTO humanBeing : humanBeingDTOList){
            if (humanBeing.getHasToothpick().equals("false")){
                humanBeing.setTeam(null);
            }
            restClient.updateHumanBeing(humanBeing);
        }
    }

    public void makeDepressive(Integer teamId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        HumanBeingDTOList humanBeingsInTeam = restClient.getHumanBeingsByTeamId(teamId);
        List<HumanBeingDTO> humanBeingDTOList = humanBeingsInTeam.getHumanBeingList();
        if (humanBeingDTOList.isEmpty()){
            throw new BadRequestException("No team with teamId " + teamId + " or this team has zero members");
        }
        for (HumanBeingDTO humanBeing : humanBeingDTOList){
            humanBeing.setMood(String.valueOf(Mood.SORROW));
            restClient.updateHumanBeing(humanBeing);
        }
    }

    public List<Team> getTeams() {
        return teamRepository.findAll();
    }

    public Team getTeam(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No team with id" + id));
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team updateTeam(Long id, Team teamToUpdate) {
        return teamRepository.findById(id)
                .map(team -> {
                    team.setName(teamToUpdate.getName());
                    return teamRepository.save(team);
                })
                .orElseThrow(() -> new NotFoundException("No team with id" + id));
    }

    public void deleteTeam(Long id) {
        teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No team with id" + id));
        teamRepository.deleteById(id);
    }
}
