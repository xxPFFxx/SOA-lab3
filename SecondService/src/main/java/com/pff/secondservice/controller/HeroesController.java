package com.pff.secondservice.controller;

import com.pff.secondservice.dto.dtoList.HumanBeingDTOList;
import com.pff.secondservice.service.HeroesService;
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

@RestController
@RequestMapping(value = "/heroes", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@CrossOrigin
public class HeroesController {
    private final HeroesService heroesService;

    public HeroesController(HeroesService heroesService){
        this.heroesService = heroesService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot";
    }

    @GetMapping("/team/{team-id}/remove-without-toothpick")
    public ResponseEntity<?> removeWithoutToothpick(@PathVariable("team-id") Integer teamId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        heroesService.removeWithoutToothpick(teamId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/team/{team-id}/make-depressive")
    public ResponseEntity<?> makeDepressive(@PathVariable("team-id") Integer teamId){
        heroesService.makeDepressive(teamId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
