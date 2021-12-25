package com.pff.secondservice.client;

import com.pff.secondservice.dto.HumanBeingDTO;
import org.springframework.web.client.RestTemplate;

public class RestClient {
    private static final String REST_URI = "https://localhost:51510/teams";

    RestTemplate restTemplate;

    public RestClient(){
        restTemplate = new RestTemplate();
    }
    public void getHumanBeingsByTeamId(Integer teamId){
        restTemplate.getForObject(REST_URI, HumanBeingDTO.class);
    }
}
