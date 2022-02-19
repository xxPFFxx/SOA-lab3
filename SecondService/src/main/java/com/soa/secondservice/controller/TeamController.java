package com.soa.secondservice.controller;

import com.soa.secondservice.exception.BadRequestException;
import com.soa.secondservice.exception.NotFoundException;
import com.soa.secondservice.models.Team;
import com.soa.secondservice.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

@RestController
@RequestMapping(value = "/heroes/team", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@CrossOrigin
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService){
        this.teamService = teamService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot";
    }

    @GetMapping
    List<Team> getTeams(){
        return teamService.getTeams();
    }

    @GetMapping("/{id}")
    Team getTeam(@PathVariable Long id){
        return teamService.getTeam(id);
    }

    @PostMapping
    Team createTeam(@RequestBody Team team){
        return teamService.createTeam(team);
    }

    @PutMapping("/{id}")
    Team updateTeam(@RequestBody Team team, @PathVariable Long id){
        return teamService.updateTeam(id, team);
    }

    @DeleteMapping("/{id}")
    void deleteTeam(@PathVariable Long id){
        teamService.deleteTeam(id);
    }

    @PostMapping("/{team-id}/remove-without-toothpick")
    public ResponseEntity<?> removeWithoutToothpick(@PathVariable("team-id") Long teamId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        teamService.removeWithoutToothpick(teamId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/{team-id}/make-depressive")
    public ResponseEntity<?> makeDepressive(@PathVariable("team-id") Long teamId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        teamService.makeDepressive(teamId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/{team-id}/human-being/{human-being-id}")
    void addHumanBeingToTeam(@PathVariable("team-id") Long teamId, @PathVariable("human-being-id") Long humanBeingId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        teamService.addHumanBeingToTeam(teamId, humanBeingId);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)

    public ResponseEntity<String> handleNotFoundException(NotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

}
