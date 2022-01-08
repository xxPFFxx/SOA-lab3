package com.pff.secondservice.client;

import com.pff.secondservice.dto.HumanBeingDTO;
import com.pff.secondservice.dto.dtoList.HumanBeingDTOList;
import com.pff.secondservice.utils.RestTemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Component
public class RestClient {
    private static final String REST_URI = "https://localhost:51510/human-beings";

    final RestTemplateConfig restTemplateConfig;

    public RestClient(RestTemplateConfig restTemplateConfig) {
        this.restTemplateConfig = restTemplateConfig;
    }

    public HumanBeingDTOList getHumanBeingsByTeamId(Integer teamId) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        return restTemplateConfig.restTemplate().getForObject(REST_URI + "?filterBy=teamId:" + teamId, HumanBeingDTOList.class);
    }

    public void updateHumanBeing(HumanBeingDTO humanBeingDTO) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        restTemplateConfig.restTemplate().put(REST_URI + "/" + humanBeingDTO.getId(), humanBeingDTO);
    }
}
