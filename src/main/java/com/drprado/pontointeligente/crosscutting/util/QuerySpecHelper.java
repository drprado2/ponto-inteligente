package com.drprado.pontointeligente.crosscutting.util;

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
}
