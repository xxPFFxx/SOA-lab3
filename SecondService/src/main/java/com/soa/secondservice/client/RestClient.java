package com.soa.secondservice.client;

import com.soa.secondservice.dto.HumanBeingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RestClient {

    private final String pathToHumanBeings = "/human-beings";

    private final String muleBaseApi;

    HttpHeaders headers = new HttpHeaders();


    @Autowired
    public RestClient(@Value("${soaspring.mule-service-api}") String muleServiceApi) {
        this.muleBaseApi = muleServiceApi;
    }

    public HumanBeingDTO getHumanBeing(Long id)  {

        return  new RestTemplate().getForObject(muleBaseApi + pathToHumanBeings + "/" + id, HumanBeingDTO.class);
    }

    public void updateHumanBeing(HumanBeingDTO humanBeingDTO) {
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<HumanBeingDTO> entity = new HttpEntity<>(humanBeingDTO, headers);
        restTemplate().put(muleBaseApi + pathToHumanBeings + "/" + humanBeingDTO.getId(), entity);
    }

    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }
}
