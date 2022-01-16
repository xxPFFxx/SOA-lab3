package com.pff.secondservice.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.pff.secondservice.enums.Mood;
import com.pff.secondservice.enums.WeaponType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "humanbeing")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class HumanBeing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotNull(message = "name must be not Null value")
    private String name; //Поле не может быть null, Строка не может быть пустой

    @ManyToOne(optional = false, cascade =  CascadeType.ALL)
    @JoinColumn(name = "coordinates_id")
    @NotNull(message = "coordinates must be not Null value")
    private Coordinates coordinates; //Поле не может быть null

    @CreationTimestamp
    @Column(name = "creationdate")
    private LocalDateTime creationDate;

    @NotNull(message = "realHero must be not Null value")
    @Column(name = "realhero")
    private Boolean realHero; //Поле не может быть null

    @Column(name = "hastoothpick")
    private Boolean hasToothpick;

    @NotNull(message = "impactSpeed must be not Null value")
    @Column(name = "impactspeed")
    private Float impactSpeed; //Поле не может быть null

    @NotNull(message = "soundtrackName must be not Null value")
    @Column(name = "soundtrackname")
    private String soundtrackName; //Поле не может быть null

    @Enumerated(EnumType.STRING)
    @Column(name = "weapontype")
    private WeaponType weaponType; //Поле может быть null

    @Enumerated(EnumType.STRING)
    private Mood mood; //Поле может быть null

    @ManyToOne(optional = false, cascade =  CascadeType.ALL)
    @JoinColumn(name = "car_id")
    @NotNull(message = "car must be not Null value")
    private Car car; //Поле не может быть null

    @ManyToOne(cascade =  CascadeType.MERGE)
    @JoinColumn(name="team_id")
    private Team team;

    public HumanBeing(@NotNull String name, @NotNull Coordinates coordinates, @NotNull Boolean realHero, boolean hasToothpick, @NotNull Float impactSpeed,
                      @NotNull String soundtrackName, WeaponType weaponType, Mood mood, @NotNull Car car, Team team){
        this.name = name;
        this.coordinates = coordinates;
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.soundtrackName = soundtrackName;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
        this.team = team;
    }
}
