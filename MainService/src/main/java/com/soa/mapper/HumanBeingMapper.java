package com.soa.mapper;

import com.soa.dto.HumanBeingDTO;
import com.soa.models.HumanBeing;
import com.soa.util.FieldValidationUtil;

import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.List;

public class HumanBeingMapper {
    private CoordinatesMapper coordinatesMapper;
    private CarMapper carMapper;
    private TeamMapper teamMapper;

    public HumanBeingMapper() {
        coordinatesMapper = new CoordinatesMapper();
        carMapper = new CarMapper();
        teamMapper = new TeamMapper();
    }

    public HumanBeing mapHumanBeingDTOToHumanBeing(HumanBeingDTO humanBeingDTO) {
        try {
            HumanBeing humanBeing = new HumanBeing();
            humanBeing.setId(FieldValidationUtil.getLongFieldValue(humanBeingDTO.getId()));
            humanBeing.setName(FieldValidationUtil.getStringValue(humanBeingDTO.getName()));
            humanBeing.setCoordinates(coordinatesMapper.mapCoordinatesDTOToCoordinates(humanBeingDTO.getCoordinates()));
            humanBeing.setRealHero(FieldValidationUtil.getBooleanFieldValue(humanBeingDTO.getRealHero()));
            humanBeing.setHasToothpick(FieldValidationUtil.getBooleanFieldValue(humanBeingDTO.getHasToothpick()));
            humanBeing.setImpactSpeed(FieldValidationUtil.getFloatFieldValue(humanBeingDTO.getImpactSpeed()));
            humanBeing.setSoundtrackName(FieldValidationUtil.getStringValue(humanBeingDTO.getSoundtrackName()));
            humanBeing.setWeaponType(FieldValidationUtil.getWeaponTypeValue(humanBeingDTO.getWeaponType()));
            humanBeing.setMood(FieldValidationUtil.getMoodValue(humanBeingDTO.getMood()));
            humanBeing.setCar(carMapper.mapCarDTOToCar(humanBeingDTO.getCar()));
            if (humanBeingDTO.getTeam() == null){
                humanBeing.setTeam(null);
            }
            else {
                humanBeing.setTeam(teamMapper.mapTeamDTOtoTeam(humanBeingDTO.getTeam()));
            }
            return humanBeing;
        } catch (NullPointerException e) {
            throw new BadRequestException("Bad format of JSON body");
        }

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
        if (humanBeing.getTeam() == null){
            humanBeingDTO.setTeam(null);
        }
        else {
            humanBeingDTO.setTeam(teamMapper.mapTeamToTeamDTO(humanBeing.getTeam()));
        }
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
