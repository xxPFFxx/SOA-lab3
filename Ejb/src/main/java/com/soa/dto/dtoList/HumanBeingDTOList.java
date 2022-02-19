package com.soa.dto.dtoList;

import com.soa.dto.HumanBeingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class HumanBeingDTOList implements Serializable {
    private List<HumanBeingDTO> humanBeingList;
    private long count;
}
