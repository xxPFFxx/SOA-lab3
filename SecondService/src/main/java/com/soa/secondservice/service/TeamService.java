package com.soa.secondservice.service;

import com.soa.secondservice.client.RestClient;
import com.soa.secondservice.dto.HumanBeingDTO;
import com.soa.secondservice.enums.Mood;
import com.soa.secondservice.exception.NotFoundException;
import com.soa.secondservice.models.HumanBeingsInTeams;
import com.soa.secondservice.models.Team;
import com.soa.secondservice.repository.HumanBeingsInTeamsRepository;
import com.soa.secondservice.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public void addHumanBeingToTeam(Long teamId, Long humanBeingId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        Optional<HumanBeingsInTeams> humanBeingInTeam = humanBeingsInTeamsRepository.findById(humanBeingId);
        try {
            restClient.getHumanBeing(humanBeingId);
        }
        catch (Exception e){
            throw new NotFoundException("No humanBeing with id " + humanBeingId);
        }
        Optional<Team> team = teamRepository.findById(teamId);
        if (!team.isPresent()){
            throw new NotFoundException("No team with id " + teamId);
        }
        if (humanBeingInTeam.isPresent()){
            HumanBeingsInTeams humanBeingInTeamToUpdate = humanBeingInTeam.get();
            humanBeingInTeamToUpdate.setTeamId(teamId);
            humanBeingsInTeamsRepository.save(humanBeingInTeamToUpdate);
        }
        else {
            humanBeingsInTeamsRepository.save(new HumanBeingsInTeams(humanBeingId, teamId));
        }

    }
}
