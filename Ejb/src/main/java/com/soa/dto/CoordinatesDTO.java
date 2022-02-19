package com.soa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class CoordinatesDTO implements Serializable {
    private String id;
    private String x;
    private String y;
}
