package net.atos.xa.contest.repository;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 */
public class EmP {
    @PersistenceContext
    @Produces
    EntityManager e;
}
