package com.pff.secondservice.service;

import com.pff.secondservice.client.RestClient;
import com.pff.secondservice.dto.HumanBeingDTO;
import com.pff.secondservice.dto.dtoList.HumanBeingDTOList;
import com.pff.secondservice.enums.Mood;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

@Service
public class HeroesService {

    private final RestClient restClient;

    public HeroesService(RestClient restClient) {
        this.restClient = restClient;
    }

    public void removeWithoutToothpick(Integer teamId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        HumanBeingDTOList humanBeingsInTeam = restClient.getHumanBeingsByTeamId(teamId);
        List<HumanBeingDTO> humanBeingDTOList = humanBeingsInTeam.getHumanBeingList();
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
        for (HumanBeingDTO humanBeing : humanBeingDTOList){
            humanBeing.setMood(String.valueOf(Mood.SORROW));
            restClient.updateHumanBeing(humanBeing);
        }
    }
}
