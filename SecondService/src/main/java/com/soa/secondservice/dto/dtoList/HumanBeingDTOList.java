package com.soa.secondservice.dto.dtoList;

import com.soa.secondservice.dto.HumanBeingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class HumanBeingDTOList {
    private List<HumanBeingDTO> humanBeingList;
    private long count;
}
