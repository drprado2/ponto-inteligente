package com.drprado.pontointeligente.crosscutting.IoC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ContextResolver {
    private static ApplicationContext context;

    @Autowired
    public ContextResolver(ApplicationContext ac){
        context = ac;
    }

    public static ApplicationContext getContext(){
        return context;
    }
}
