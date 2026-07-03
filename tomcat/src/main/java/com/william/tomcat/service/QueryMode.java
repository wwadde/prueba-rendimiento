package com.william.tomcat.service;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum QueryMode {

    JDBC,
    JPA;

    public static QueryMode fromValue(String value){
        return Arrays.stream(QueryMode.values())
                .filter(mode -> mode.name().equalsIgnoreCase(value))
                .findFirst()
                // por defecto
                .orElse(QueryMode.JPA);
    }

}
