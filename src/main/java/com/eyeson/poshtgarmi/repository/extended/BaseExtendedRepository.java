package com.eyeson.poshtgarmi.repository.extended;

import javax.persistence.EntityManager;

/**
 * Created by saeed on 9/30/2016.
 */
public interface BaseExtendedRepository<T> {
     EntityManager getEm();

}
