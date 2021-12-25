package com.soa;

import com.soa.controllers.HumanBeingController;
import com.soa.exceptions.BadRequestMapper;
import com.soa.exceptions.NotFoundMapper;
import com.soa.filter.CorsFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("")
public class CallerApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(HumanBeingController.class);
        resources.add(CorsFilter.class);
        resources.add(BadRequestMapper.class);
        resources.add(NotFoundMapper.class);
        return resources;
    }

}