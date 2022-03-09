package com.soa.mapper;

import com.soa.dto.CarDTO;
import com.soa.models.Car;

public class CarMapper {

    public CarDTO mapCarToCarDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(String.valueOf(car.getId()));
        carDTO.setName(String.valueOf(car.getName()));
        carDTO.setCool(String.valueOf(car.getCool()));
        return carDTO;
    }

}
