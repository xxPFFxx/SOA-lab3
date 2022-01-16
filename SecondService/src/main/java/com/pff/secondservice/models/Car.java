package com.pff.secondservice.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name = "car")
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Car {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name; //Поле может быть null

    private Boolean cool; //Поле может быть null

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    private List<HumanBeing> humanBeings;

    public Car(String name, Boolean cool){
        this.name = name;
        this.cool = cool;
    }
}