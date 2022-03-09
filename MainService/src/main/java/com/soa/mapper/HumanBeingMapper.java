package com.soa.mapper;

import com.soa.dto.HumanBeingDTO;
import com.soa.models.HumanBeing;

import java.util.ArrayList;
import java.util.List;

public class HumanBeingMapper {
    private CoordinatesMapper coordinatesMapper;
    private CarMapper carMapper;

    public HumanBeingMapper() {
        coordinatesMapper = new CoordinatesMapper();
        carMapper = new CarMapper();
    }

    public HumanBeingDTO mapHumanBeingToHumanBeingDTO(HumanBeing humanBeing) {
        HumanBeingDTO humanBeingDTO = new HumanBeingDTO();
        humanBeingDTO.setId(String.valueOf(humanBeing.getId()));
        humanBeingDTO.setName(String.valueOf(humanBeing.getName()));
        humanBeingDTO.setCoordinates(coordinatesMapper.mapCoordinatesToCoordinatesDTO(humanBeing.getCoordinates()));
        humanBeingDTO.setCreationDate(String.valueOf(humanBeing.getCreationDate()));
        humanBeingDTO.setRealHero(String.valueOf(humanBeing.getRealHero()));
        humanBeingDTO.setHasToothpick(String.valueOf(humanBeing.getHasToothpick()));
        humanBeingDTO.setImpactSpeed(String.valueOf(humanBeing.getImpactSpeed()));
        humanBeingDTO.setSoundtrackName(String.valueOf(humanBeing.getSoundtrackName()));
        humanBeingDTO.setWeaponType(String.valueOf(humanBeing.getWeaponType()));
        humanBeingDTO.setMood(String.valueOf(humanBeing.getMood()));
        humanBeingDTO.setCar(carMapper.mapCarToCarDTO(humanBeing.getCar()));
        return humanBeingDTO;
    }

    public List<HumanBeingDTO> mapHumanBeingListToHumanBeingDTOList(List<HumanBeing> humanBeingList) {
        ArrayList<HumanBeingDTO> humanBeingDTOList = new ArrayList<>();
        for (HumanBeing humanBeing : humanBeingList) {
            humanBeingDTOList.add(mapHumanBeingToHumanBeingDTO(humanBeing));
        }
        return humanBeingDTOList;
    }
}
