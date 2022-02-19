package com.soa.secondservice.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "humanbeingsinteam")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class HumanBeingsInTeams {
    @Id
    private Long userId;

//    @ManyToOne(optional = false, cascade =  CascadeType.ALL)
//    @JoinColumn(name = "team_id")
//    private Team team;
    private Long teamId;
}
