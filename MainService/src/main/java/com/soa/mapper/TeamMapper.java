package com.soa.mapper;

import com.soa.dto.TeamDTO;
import com.soa.models.Team;
import com.soa.util.FieldValidationUtil;

import javax.ws.rs.BadRequestException;

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
