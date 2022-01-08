package com.soa.repository;


import org.hibernate.Session;
import org.postgresql.util.PSQLException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    List<T> findByCriteria(CriteriaQuery<T> criteriaQuery);

    Optional<T> findById(Integer id);

    T update(T coordinates);

    void save(T coordinates) throws PSQLException;

    void deleteById(Integer id);



    EntityManager createEntityManager();

    Session openSession();
}