package com.soa.services;

import com.soa.dto.PagedHumanBeingList;
import com.soa.exceptions.EjbNotAvailableException;
import com.soa.models.HumanBeing;

import javax.ejb.Remote;
import javax.ws.rs.core.Response;

@Remote
public interface HumanBeingServiceInterface {

    Response additionalTasks(String weaponTypeCount, String weaponTypeArray, String uniqueImpactSpeed);

    PagedHumanBeingList getHumanBeings(String perPage, String curPage, String sortBy, String filterBy);

    HumanBeing getHumanBeing(Long long_id);

    void saveHumanBeing(String humanBeing);

    void updateHumanBeing(String humanBeing, Long long_id);

    void deleteHumanBeing(Long long_id);
}
