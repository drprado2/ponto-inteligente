package com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI;

import org.springframework.data.jpa.domain.Specification;

public final class QuerySpecHelper {
    private QuerySpecHelper() {
    }

    public static String getLikePattern(String term){
        if(term == null || term.isEmpty()){
            return "%";
        }else{
            return "%" + term.toLowerCase() + "%";
        }
    }

    public static Specification specAlways(){
        return (root, query, cb) -> cb.conjunction();
    }

    public static Specification specNever(){
        return (root, query, cb) -> cb.disjunction();
    }
}
