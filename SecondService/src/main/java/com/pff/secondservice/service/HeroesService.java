package com.pff.secondservice.service;

import com.pff.secondservice.client.RestClient;
import org.springframework.stereotype.Service;

@Service
public class HeroesService {

    private final RestClient restClient;

    public HeroesService(){
        this.restClient = new RestClient();
    }
    public void removeWithoutToothpick(Integer teamId){

    }

    public void makeDepressive(Integer teamId){

    }
}
