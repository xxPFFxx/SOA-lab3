package com.soa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.soa.models.HumanBeing;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PagedHumanBeingList implements Serializable {
    private List<HumanBeing> humanBeingList;
    private Long count;
}