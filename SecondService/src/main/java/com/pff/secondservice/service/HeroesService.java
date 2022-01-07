package com.pff.secondservice.service;

import com.pff.secondservice.client.RestClient;
import com.pff.secondservice.dto.HumanBeingDTO;
import com.pff.secondservice.dto.dtoList.HumanBeingDTOList;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Service
public class HeroesService {

    private final RestClient restClient;

    public HeroesService(){
        this.restClient = new RestClient();
    }
    public HumanBeingDTOList removeWithoutToothpick(Integer teamId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        return restClient.getHumanBeingsByTeamId(teamId);
    }

    public void makeDepressive(Integer teamId){

    }
}
