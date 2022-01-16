package com.pff.secondservice.mapper;

import com.pff.secondservice.dto.TeamDTO;
import com.pff.secondservice.exception.BadRequestException;
import com.pff.secondservice.models.Team;
import com.pff.secondservice.utils.FieldValidationUtil;

public class TeamMapper {
    public Team mapTeamDTOtoTeam(TeamDTO teamDTO) {
        try {
            Team team = new Team();
            team.setId(FieldValidationUtil.getLongFieldValue(teamDTO.getId()));
            team.setName(FieldValidationUtil.getStringValue(teamDTO.getName()));
            return team;
        } catch (NullPointerException e) {
            throw new BadRequestException("Bad format of JSON body");
        }
    }

    public TeamDTO mapTeamToTeamDTO(Team team){
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setId(String.valueOf(team.getId()));
        teamDTO.setName(String.valueOf(team.getName()));
        return teamDTO;
    }
}
