package com.soa.dto;

import com.soa.models.HumanBeing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PagedHumanBeingList implements Serializable {
    private List<HumanBeing> humanBeingList;
    private Long count;
}