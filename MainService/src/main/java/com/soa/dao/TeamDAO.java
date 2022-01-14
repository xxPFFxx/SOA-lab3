package com.soa.dao;

import com.soa.models.Team;
import com.soa.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TeamDAO {
    public List<Team> findAll() {
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Team> criteriaQuery = criteriaBuilder.createQuery(Team.class);
            Root<Team> from = criteriaQuery.from(Team.class);
            CriteriaQuery<Team> select = criteriaQuery.select(from);
            TypedQuery<Team> typedQuery = session.createQuery(select);
            return typedQuery.getResultList();
        } catch (Exception e){
            if (transaction != null) transaction.rollback();
            throw e;
        }

    }


}
