package com.pff.secondservice.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "coordinates")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(value = "x must be not Null value")
    @Max(value = 416, message = "x must be <= 416")
    //@DecimalMax(value = "416.0", message = "x must be <= 416")
    private Integer x; //Максимальное значение поля: 416, Поле не может быть null

    @NotNull(value = "y must be not Null value")
    @Min(value = -700L, message = "y must be > -700")
    //@DecimalMin(value = "-700.0", inclusive = false, message = "y must be > -700")
    private Long y; //Значение поля должно быть больше -700, Поле не может быть null

    @OneToMany(mappedBy = "coordinates", fetch = FetchType.EAGER)
    private List<HumanBeing> humanBeings;

    public Coordinates(Integer x, Long y){
        this.x = x;
        this.y = y;
    }
}
