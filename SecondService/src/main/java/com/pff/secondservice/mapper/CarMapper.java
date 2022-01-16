package com.pff.secondservice.mapper;

import com.pff.secondservice.dto.CarDTO;
import com.pff.secondservice.exception.BadRequestException;
import com.pff.secondservice.models.Car;
import com.pff.secondservice.utils.FieldValidationUtil;

public class CarMapper {
    public Car mapCarDTOToCar(CarDTO carDTO) {
        try {
            Car car = new Car();
            car.setId(FieldValidationUtil.getLongFieldValue(carDTO.getId()));
            car.setName(FieldValidationUtil.getStringValue(carDTO.getName()));
            car.setCool(FieldValidationUtil.getBooleanFieldValue(carDTO.getCool()));
            return car;
        }        catch (NullPointerException e) {
            throw new BadRequestException("Bad format of JSON body");
        }
    }

    public CarDTO mapCarToCarDTO(Car car) {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(String.valueOf(car.getId()));
        carDTO.setName(String.valueOf(car.getName()));
        carDTO.setCool(String.valueOf(car.getCool()));
        return carDTO;
    }

}
