package com.pff.secondservice.client;

import com.pff.secondservice.dto.HumanBeingDTO;
import com.pff.secondservice.dto.dtoList.HumanBeingDTOList;
import com.pff.secondservice.utils.RestTemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Component
public class RestClient {

    private final String REST_URI;
    private final String pathToHumanBeings = "/human-beings";

    final RestTemplateConfig restTemplateConfig;

    @Autowired
    public RestClient(@Value("${soaspring.apiServiceUrl}") String apiServiceUrl, RestTemplateConfig restTemplateConfig) {
        REST_URI = apiServiceUrl;
        this.restTemplateConfig = restTemplateConfig;
    }

    public HumanBeingDTOList getHumanBeings() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        return restTemplateConfig.restTemplate().getForObject(REST_URI + pathToHumanBeings, HumanBeingDTOList.class);
    }

    public void updateHumanBeing(HumanBeingDTO humanBeingDTO) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        restTemplateConfig.restTemplate().put(REST_URI + pathToHumanBeings + "/" + humanBeingDTO.getId(), humanBeingDTO);
    }
}
