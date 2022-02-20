package com.soa.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.soa.dao.HumanBeingDAO;
import com.soa.dto.CountDTO;
import com.soa.dto.HumanBeingDTO;
import com.soa.dto.PagedHumanBeingList;
import com.soa.dto.dtoList.HumanBeingDTOList;
import com.soa.enums.WeaponType;
import com.soa.exceptions.BadRequestException;
import com.soa.exceptions.NotFoundException;
import com.soa.mapper.HumanBeingMapper;
import com.soa.models.HumanBeing;
import com.soa.util.HibernateUtil;
import lombok.SneakyThrows;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Stateless
public class HumanBeingService implements HumanBeingServiceInterface {

    private final HumanBeingDAO humanBeingDAO;
    private final Gson gson;
    private final HumanBeingMapper humanBeingMapper;
    private Session session;
    private EntityManager em;

    public HumanBeingService(){
        humanBeingDAO = new HumanBeingDAO();
        gson = new GsonBuilder().setPrettyPrinting().create();
        humanBeingMapper = new HumanBeingMapper();
        session = HibernateUtil.getSessionFactory().openSession();
        em = session.getEntityManagerFactory().createEntityManager();

    }

    @SneakyThrows
    public void updateHumanBeing(String humanBeingRequest, Long id) {
        try {
            if (id != null) {
                try {
                    HumanBeingDTO humanBeingDTO = gson.fromJson(humanBeingRequest, HumanBeingDTO.class);
                    HumanBeing humanBeingToUpdate = humanBeingMapper.mapHumanBeingDTOToHumanBeing(humanBeingDTO);
                    Optional<HumanBeing> humanBeing = humanBeingDAO.getHumanBeing(id);
                    humanBeingToUpdate.setCreationDate(humanBeing.get().getCreationDate());
                    humanBeingToUpdate.setId(id.longValue());
                    humanBeingDAO.updateHumanBeing(humanBeingToUpdate);
                } catch (NumberFormatException e) {
                    throw new BadRequestException("Bad format of id: " + id + ", should be natural number (1,2,...)");
                } catch (NoResultException | NoSuchElementException e) {
                    throw new NotFoundException("No HumanBeing with id " + id);
                }
            }
        }catch (JsonSyntaxException e){
            throw new BadRequestException("Bad syntax of JSON body");
        }
    }

    @SneakyThrows
    public void saveHumanBeing(String humanBeing){
        try {
            HumanBeingDTO humanBeingDTO = gson.fromJson(humanBeing, HumanBeingDTO.class);
            HumanBeing humanBeingToPersist = humanBeingMapper.mapHumanBeingDTOToHumanBeing(humanBeingDTO);
            humanBeingDAO.createHumanBeing(humanBeingToPersist);
        }catch (JsonSyntaxException e){
            throw new BadRequestException("Bad syntax of JSON body");
        }
    }

    @SneakyThrows
    public void deleteHumanBeing(Long id){
        try {
            HumanBeing humanBeing = (humanBeingDAO.getHumanBeing(id)).orElseThrow(() -> new NotFoundException("humanBeing with id = " + id + " does not exist"));
            humanBeingDAO.deleteHumanBeing(id);
        } catch (NumberFormatException e) {
                throw new BadRequestException("Bad format of id: " + id + ", should be natural number (1,2,...)");

        } catch (NoResultException e) {
                throw new NotFoundException("No HumanBeing with id " + id);
        }
    }

    @SneakyThrows
    @Override
    public Response additionalTasks(String weaponTypeCount, String weaponTypeArray, String uniqueImpactSpeed) {
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

    public PagedHumanBeingList getHumanBeings(String perPage, String curPage, String sortBy, String filterBy) {
        PagedHumanBeingList pagedHumanBeingList = humanBeingDAO.findAll(perPage, curPage, sortBy, filterBy);
        return pagedHumanBeingList;
    }

    @SneakyThrows
    public HumanBeing getHumanBeing(Long id){
        try {
            return (humanBeingDAO.getHumanBeing(id)).orElseThrow(() -> new NotFoundException("humanBeing with id = " + id + " does not exist"));
        }catch (NoResultException e){
            throw new NotFoundException("humanBeing with id = " + id + " does not exist");
        }

    }
}
