package com.pff.secondservice.service;

import com.pff.secondservice.client.RestClient;
import com.pff.secondservice.dto.HumanBeingDTO;
import com.pff.secondservice.enums.Mood;
import com.pff.secondservice.exception.NotFoundException;
import com.pff.secondservice.models.HumanBeingsInTeams;
import com.pff.secondservice.models.Team;
import com.pff.secondservice.repository.HumanBeingsInTeamsRepository;
import com.pff.secondservice.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    private final RestClient restClient;
    private final TeamRepository teamRepository;
    private final HumanBeingsInTeamsRepository humanBeingsInTeamsRepository;

    public TeamService(RestClient restClient, TeamRepository teamRepository, HumanBeingsInTeamsRepository humanBeingsInTeamsRepository) {
        this.restClient = restClient;
        this.teamRepository = teamRepository;
        this.humanBeingsInTeamsRepository = humanBeingsInTeamsRepository;
    }

    public void removeWithoutToothpick(Long teamId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("No team with id" + teamId));
        ArrayList<HumanBeingsInTeams> humanBeingsInTeamsToDelete = new ArrayList<>();
        ArrayList<HumanBeingsInTeams> humanBeingsInTeams = humanBeingsInTeamsRepository.findAllByTeamId(teamId);
        for (HumanBeingsInTeams humanBeingInTeam : humanBeingsInTeams){
            HumanBeingDTO humanBeing = restClient.getHumanBeing(humanBeingInTeam.getUserId());
            if (humanBeing.getHasToothpick().equals("false")){
                humanBeingsInTeamsToDelete.add(humanBeingInTeam);
            }
        }
        humanBeingsInTeamsRepository.deleteAll(humanBeingsInTeamsToDelete);
    }

    public void makeDepressive(Long teamId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException("No team with id" + teamId));
        ArrayList<HumanBeingsInTeams> humanBeingsInTeams = humanBeingsInTeamsRepository.findAllByTeamId(teamId);
        for (HumanBeingsInTeams humanBeingInTeam : humanBeingsInTeams){
            HumanBeingDTO humanBeing = restClient.getHumanBeing(humanBeingInTeam.getUserId());
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
