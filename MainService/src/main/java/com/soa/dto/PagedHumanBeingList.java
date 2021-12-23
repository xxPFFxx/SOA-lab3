package com.soa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.soa.models.HumanBeing;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PagedHumanBeingList {
    private List<HumanBeing> humanBeingList;
    private Long count;
}