package com.soa.util;

import com.soa.dto.PagedHumanBeingList;
import com.soa.exceptions.EjbNotAvailableException;
import com.soa.models.HumanBeing;
import com.soa.services.HumanBeingServiceInterface;
import lombok.SneakyThrows;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import java.util.Hashtable;
import java.util.Properties;

public class RemoteBeanUtil {
    public static HumanBeingServiceInterface lookupRemoteStatelessBean() {
        Properties contextProperties = new Properties();
        contextProperties.setProperty(
                Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.enterprise.naming.SerialInitContextFactory"
        );
        try {
            InitialContext context = new InitialContext(contextProperties);
            String appName = "global";
            String moduleName = "Ejb-1.0-SNAPSHOT";
            String beanName = "HumanBeingService";
            String lookupName = "java:" + appName + "/" + moduleName + "/" + beanName;
            return (HumanBeingServiceInterface) context.lookup(lookupName);
        } catch (NamingException e) {
            return new HumanBeingServiceInterface() {
                @SneakyThrows
                @Override
                public Response additionalTasks(String weaponTypeCount, String weaponTypeArray, String uniqueImpactSpeed) {
                    throw new EjbNotAvailableException("Не удалось получить доступ к EJB HumanBeingService");
                }
                @SneakyThrows
                @Override
                public PagedHumanBeingList getHumanBeings(String perPage, String curPage, String sortBy, String filterBy) {
                    throw new EjbNotAvailableException("Не удалось получить доступ к EJB HumanBeingService");
                }
                @SneakyThrows
                @Override
                public HumanBeing getHumanBeing(Long long_id) {
                    throw new EjbNotAvailableException("Не удалось получить доступ к EJB HumanBeingService");
                }
                @SneakyThrows
                @Override
                public void saveHumanBeing(String humanBeing) {
                    throw new EjbNotAvailableException("Не удалось получить доступ к EJB HumanBeingService");
                }
                @SneakyThrows
                @Override
                public void updateHumanBeing(String humanBeing, Long long_id) {
                    throw new EjbNotAvailableException("Не удалось получить доступ к EJB HumanBeingService");
                }
                @SneakyThrows
                @Override
                public void deleteHumanBeing(Long long_id) {
                    throw new EjbNotAvailableException("Не удалось получить доступ к EJB HumanBeingService");
                }
            };
        }
    }
}

