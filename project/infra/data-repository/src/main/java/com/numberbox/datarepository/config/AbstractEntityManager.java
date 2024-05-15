package com.numberbox.datarepository.config;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractEntityManager {
    @Autowired
    protected EntityManager entityManager;
}
