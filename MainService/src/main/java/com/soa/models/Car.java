package com.soa.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Entity
@Table(name = "car")
@Setter
public class Car implements Serializable {
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