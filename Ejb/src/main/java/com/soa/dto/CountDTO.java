package com.soa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter

@NoArgsConstructor
public class CountDTO implements Serializable {
    private Long count;
}