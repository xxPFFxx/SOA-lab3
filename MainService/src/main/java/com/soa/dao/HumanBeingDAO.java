package com.soa.dao;

import com.soa.dto.PagedHumanBeingList;
import com.soa.enums.Mood;
import com.soa.enums.WeaponType;
import com.soa.models.HumanBeing;
import com.soa.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.soa.util.UrlParametersUtil.parseInteger;

public class HumanBeingDAO {
    public Optional<HumanBeing> getHumanBeing(Long id) {
        Transaction transaction;
        HumanBeing humanBeing;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            humanBeing = session.find(HumanBeing.class, id);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return Optional.ofNullable(humanBeing);
    }

    public PagedHumanBeingList findAll(String perPage, String curPage, String sortBy, String filterBy) {

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<HumanBeing> criteriaQuery = criteriaBuilder.createQuery(HumanBeing.class);
            Root<HumanBeing> from = criteriaQuery.from(HumanBeing.class);
            CriteriaQuery<HumanBeing> select = criteriaQuery.select(from);

            List<Order> orderList = getOrderList(sortBy, criteriaBuilder, from);
            if (!orderList.isEmpty())
                criteriaQuery.orderBy(orderList);

            ArrayList<Predicate> predicates = getPredicatesList(filterBy, criteriaBuilder, from);
            if (!predicates.isEmpty())
                select.where(predicates.toArray(new Predicate[]{}));

            PagedHumanBeingList pagedHumanBeingList = new PagedHumanBeingList();

            pagedHumanBeingList.setCount(getOverallCount(criteriaBuilder, predicates, session));
            pagedHumanBeingList.setHumanBeingList(findAll(perPage, curPage, select, session));

            return pagedHumanBeingList;
        }


    }

    private List<HumanBeing> findAll(String perPage, String curPage, CriteriaQuery<HumanBeing> select, Session session) {
        try {
            if (perPage != null && curPage != null) {

                int pageNumber = parseInteger(curPage);
                int pageSize = parseInteger(perPage);
                if (pageNumber <= 0) throw new BadRequestException("Bad format of pageNumber: " + pageNumber + ", should be natural number (1,2,...)");
                if (pageSize < 0) throw new BadRequestException("Bad format of pageSize: " + pageSize + ", should be non-negative integer number (0,1,...)");
                TypedQuery<HumanBeing> typedQuery = session.createQuery(select);
                typedQuery.setFirstResult((pageNumber - 1) * pageSize);
                typedQuery.setMaxResults(pageSize);
                return typedQuery.getResultList();
            } else
                return findAll(select, session);
        }catch (NumberFormatException e) {
            throw new BadRequestException("Bad format of pageSize or pageNumber. They should be integer numbers (1,2,...)");
        }

    }

    private List<HumanBeing> findAll(CriteriaQuery<HumanBeing> select, Session session) {
        TypedQuery<HumanBeing> typedQuery = session.createQuery(select);
        return typedQuery.getResultList();
    }

    private Long getOverallCount(CriteriaBuilder criteriaBuilder, ArrayList<Predicate> predicates, Session session) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(HumanBeing.class)));
        session.createQuery(countQuery);
        if (predicates.size() > 0)
            countQuery.where(predicates.toArray(new Predicate[]{}));
        return session.createQuery(countQuery).getSingleResult();

    }

    private List<Order> getOrderList(String sortBy, CriteriaBuilder criteriaBuilder, Root<HumanBeing> from) {
        List<Order> orderList = new ArrayList();
        if (sortBy != null) {
            List<String> criteria = new ArrayList<>(Arrays.asList(sortBy.split(";")));
            for (String criterion : criteria) {
                boolean order = String.valueOf(criterion.charAt(0)).equals(" ");
                switch (criterion.substring(1)) {
                    case ("id"):
                        if (order){
                            orderList.add(criteriaBuilder.asc(from.get("id")));
                        }
                        else {
                            orderList.add(criteriaBuilder.desc(from.get("id")));
                        }
                        break;
                    case ("name"):
                        if (order){
                            orderList.add(criteriaBuilder.asc(from.get("name")));
                        }
                        else {
                            orderList.add(criteriaBuilder.desc(from.get("name")));
                        }
                        break;
                    case ("realHero"):
                        if (order){
                            orderList.add(criteriaBuilder.asc(from.get("realHero")));
                        }
                        else {
                            orderList.add(criteriaBuilder.desc(from.get("realHero")));
                        }
                        break;
                    case ("hasToothpick"):
                        if (order){
                            orderList.add(criteriaBuilder.asc(from.get("hasToothpick")));
                        }
                        else {
                            orderList.add(criteriaBuilder.desc(from.get("hasToothpick")));
                        }
                        break;
                    case ("impactSpeed"):
                        if (order){
                            orderList.add(criteriaBuilder.asc(from.get("impactSpeed")));
                        }
                        else {
                            orderList.add(criteriaBuilder.desc(from.get("impactSpeed")));
                        }
                        break;
                    case ("soundtrackName"):
                        if (order){
                            orderList.add(criteriaBuilder.asc(from.get("soundtrackName")));
                        }
                        else {
                            orderList.add(criteriaBuilder.desc(from.get("soundtrackName")));
                        }
                        break;
                    case ("creationDate"):
                        if (order){
                            orderList.add(criteriaBuilder.asc(from.get("creationDate")));
                        }
                        else {
                            orderList.add(criteriaBuilder.desc(from.get("creationDate")));
                        }
                        break;
                    case ("coordinates"):
                        if (order){
                            orderList.add(criteriaBuilder.asc(from.get("coordinates").get("x")));
                        }
                        else {
                            orderList.add(criteriaBuilder.desc(from.get("coordinates").get("x")));
                        }
                        break;
                    case ("car"):
                        if (order){
                            orderList.add(criteriaBuilder.asc(from.get("car").get("name")));
                        }
                        else {
                            orderList.add(criteriaBuilder.desc(from.get("car").get("name")));
                        }
                        break;
                    case ("weaponType"):
                        if (order){
                            orderList.add(criteriaBuilder.asc(from.get("weaponType")));
                        }
                        else {
                            orderList.add(criteriaBuilder.desc(from.get("weaponType")));
                        }
                        break;
                    case ("mood"):
                        if (order){
                            orderList.add(criteriaBuilder.asc(from.get("mood")));
                        }
                        else {
                            orderList.add(criteriaBuilder.desc(from.get("mood")));
                        }
                        break;
                    case ("team"):
                        if (order){
                            orderList.add(criteriaBuilder.asc(from.get("team").get("name")));
                        }
                        else {
                            orderList.add(criteriaBuilder.desc(from.get("team").get("name")));
                        }
                        break;
                }
            }
        }
        return orderList;
    }

    private ArrayList<Predicate> getPredicatesList(String filterBy, CriteriaBuilder criteriaBuilder, Root<HumanBeing> from) {
        try {
            ArrayList<Predicate> predicates = new ArrayList<>();
            if (filterBy != null && !filterBy.isEmpty()) {
                List<String> notParsedFilters = new ArrayList<>(Arrays.asList(filterBy.split(";")));
                for (String filterString : notParsedFilters) {
                    List<String> filter = new ArrayList<>(Arrays.asList(filterString.split(":")));
                    switch (filter.get(0)) {
                        case ("id"):
                            predicates.add(criteriaBuilder.equal(from.get("id"), Integer.parseInt(filter.get(1))));
                            break;
                        case ("name"):
                            predicates.add(criteriaBuilder.equal(from.get("name"), filter.get(1)));
                            break;
                        case ("coordinates"):
                            List<String> coordinates = new ArrayList<>(Arrays.asList(filter.get(1).split(",")));
                            double double_x = Double.parseDouble(coordinates.get(0));
                            double double_y = Double.parseDouble(coordinates.get(1));
                            Predicate x = criteriaBuilder.equal(from.get("coordinates").get("x"), double_x);
                            Predicate y = criteriaBuilder.equal(from.get("coordinates").get("y"), double_y);
                            predicates.add(criteriaBuilder.and(x, y));
                            break;
                        case ("creationDate"):
                            List<String> minutesFormat = new ArrayList<>(Arrays.asList(filter.get(2).split(",")));
                            List<String> dateParameters = new ArrayList<>();
                            dateParameters.add((filter.get(1) + ":" + minutesFormat.get(0)).replace("T", " "));
                            dateParameters.add(minutesFormat.get(1));
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            if (dateParameters.get(1).equals("after")){
                                predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("creationDate"), LocalDateTime.parse(dateParameters.get(0), formatter)));
                            }
                            if (dateParameters.get(1).equals("before")){
                                predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("creationDate"), LocalDateTime.parse(dateParameters.get(0), formatter)));
                            }
                            break;
                        case ("realHero"):
                            predicates.add(criteriaBuilder.equal(from.get("realHero"), Boolean.parseBoolean(filter.get(1))));
                            break;
                        case ("hasToothpick"):
                            predicates.add(criteriaBuilder.equal(from.get("hasToothpick"), Boolean.parseBoolean(filter.get(1))));
                            break;
                        case ("impactSpeed"):
                            predicates.add(criteriaBuilder.equal(from.get("impactSpeed"), Float.parseFloat(filter.get(1))));
                            break;
                        case ("weaponType"):
                            predicates.add(criteriaBuilder.equal(from.get("weaponType"), WeaponType.valueOf(filter.get(1))));
                            break;
                        case ("mood"):
                            predicates.add(criteriaBuilder.equal(from.get("mood"), Mood.valueOf(filter.get(1))));
                            break;
                        case ("soundtrackName"):
                            predicates.add(criteriaBuilder.equal(from.get("soundtrackName"), filter.get(1)));
                            break;
                        case ("car"):
                            predicates.add(criteriaBuilder.equal(from.get("car").get("name"), filter.get(1)));
                            break;
                        case ("teamId"):
                            predicates.add(criteriaBuilder.equal(from.get("team").get("id"), Long.parseLong(filter.get(1))));
                            break;
                        case ("team"):
                            predicates.add(criteriaBuilder.equal(from.get("team").get("name"), filter.get(1)));
                            break;
                        default:
                            throw new BadRequestException("Bad format of com.soa.filter: no such field " + filter.get(0));
                    }

                }
            }

            return predicates;
        } catch (IndexOutOfBoundsException | IllegalArgumentException | DateTimeParseException e){
            throw new BadRequestException("Bad format of com.soa.filter");
        }

    }

    public void updateHumanBeing(HumanBeing humanBeing) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.update(humanBeing);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public long createHumanBeing(HumanBeing humanBeing){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Long id = (Long) session.save(humanBeing);
            transaction.commit();
            return id;
        } catch (Exception e){
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public boolean deleteHumanBeing(Long id) {
        Transaction transaction = null;
        boolean successful = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            HumanBeing humanBeing = session.find(HumanBeing.class, id);
            if (humanBeing != null) {
                session.delete(humanBeing);
                session.flush();
                successful = true;
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return successful;
    }


}
