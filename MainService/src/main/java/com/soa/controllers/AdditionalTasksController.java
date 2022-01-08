package com.soa.controllers;


import com.soa.dto.CountDTO;
import com.soa.dto.PagedHumanBeingList;
import com.soa.dto.dtoList.HumanBeingDTOList;
import com.soa.enums.WeaponType;
import com.soa.mapper.HumanBeingMapper;
import com.soa.models.HumanBeing;
import com.soa.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("additional")
public class AdditionalTasksController {
    private Session session;
    private EntityManager em;
    private HumanBeingMapper humanBeingMapper;

    public AdditionalTasksController() {
        session = HibernateUtil.getSessionFactory().openSession();
        em = session.getEntityManagerFactory().createEntityManager();
        humanBeingMapper = new HumanBeingMapper();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet(@QueryParam("weaponTypeCount") String weaponTypeCount,
                          @QueryParam("weaponTypeArray") String weaponTypeArray,
                          @QueryParam("uniqueImpactSpeed") String uniqueImpactSpeed) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        CriteriaQuery<HumanBeing> criteriaQuery = criteriaBuilder.createQuery(HumanBeing.class);
        Root<HumanBeing> from = criteriaQuery.from(HumanBeing.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(HumanBeing.class)));
        em.createQuery(countQuery);

        if (weaponTypeCount != null) {
            try {
                countQuery.where(criteriaBuilder.greaterThan(from.get("weaponType"), WeaponType.valueOf(weaponTypeArray)));
            }catch (IllegalArgumentException e){
                throw new BadRequestException("No such weaponType:" + weaponTypeArray);
            }

            Long countResult = em.createQuery(countQuery).getSingleResult();
            CountDTO countDTO = new CountDTO();
            countDTO.setCount(countResult);
            Response.ok().entity(countDTO).build();
        }
        if (weaponTypeArray != null) {
            try {
                criteriaQuery.where(criteriaBuilder.greaterThan(from.get("weaponType"), WeaponType.valueOf(weaponTypeArray)));
            }catch (IllegalArgumentException e){
                throw new BadRequestException("No such weaponType:" + weaponTypeArray);
            }

            List<HumanBeing> humanBeingList = em.createQuery(criteriaQuery).getResultList();
            PagedHumanBeingList pagedHumanBeingList = new PagedHumanBeingList();
            pagedHumanBeingList.setHumanBeingList(humanBeingList);
            pagedHumanBeingList.setCount(Long.parseLong(String.valueOf(humanBeingList.size())));
            HumanBeingDTOList dto = new HumanBeingDTOList((humanBeingMapper.mapHumanBeingListToHumanBeingDTOList(pagedHumanBeingList.getHumanBeingList())), pagedHumanBeingList.getCount());
            return Response.ok().entity(dto).build();
        }
        if (uniqueImpactSpeed != null){
            criteriaQuery.select(from.get("impactSpeed")).distinct(true);
            List<HumanBeing> humanBeingList = em.createQuery(criteriaQuery).getResultList();
            return Response.ok().entity(humanBeingList).build();
        }
        return Response.ok().build();
    }

}

