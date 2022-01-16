package com.pff.secondservice.service;

import com.pff.secondservice.client.RestClient;
import com.pff.secondservice.dto.HumanBeingDTO;
import com.pff.secondservice.dto.dtoList.HumanBeingDTOList;
import com.pff.secondservice.enums.Mood;
import com.pff.secondservice.exception.NotFoundException;
import com.pff.secondservice.models.HumanBeing;
import com.pff.secondservice.models.Team;
import com.pff.secondservice.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class TeamService {

    private final RestClient restClient;
    private final TeamRepository teamRepository;

    public TeamService(RestClient restClient, TeamRepository teamRepository) {
        this.restClient = restClient;
        this.teamRepository = teamRepository;
    }

    public void removeWithoutToothpick(Long teamId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("No team with id" + teamId));
        ArrayList<String> teamMembersIds = new ArrayList<>();
        for (HumanBeing humanBeing : team.getHumanBeings()){
            teamMembersIds.add(String.valueOf(humanBeing.getId()));
        }
        HumanBeingDTOList humanBeings = restClient.getHumanBeings();
        List<HumanBeingDTO> humanBeingDTOList = humanBeings.getHumanBeingList();
        for (HumanBeingDTO humanBeing : humanBeingDTOList){
            if (humanBeing.getHasToothpick().equals("false") && teamMembersIds.contains(humanBeing.getId())){
                new HashSet<>(team.getHumanBeings())
                        .forEach(humanBeing1 -> {
                            if (humanBeing1.getId() == Long.parseLong(humanBeing.getId())){
                                team.removeHumanBeing(humanBeing1);
                            }
                        });
            }
            restClient.updateHumanBeing(humanBeing);
        }
        teamRepository.save(team);
    }

    public void makeDepressive(Long teamId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("No team with id" + teamId));
        ArrayList<String> teamMembersIds = new ArrayList<>();
        for (HumanBeing humanBeing : team.getHumanBeings()){
            teamMembersIds.add(String.valueOf(humanBeing.getId()));
        }
        HumanBeingDTOList humanBeings = restClient.getHumanBeings();
        List<HumanBeingDTO> humanBeingDTOList = humanBeings.getHumanBeingList();
        for (HumanBeingDTO humanBeing : humanBeingDTOList){
            if (teamMembersIds.contains(humanBeing.getId())){
                humanBeing.setMood(String.valueOf(Mood.SORROW));
            }
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
