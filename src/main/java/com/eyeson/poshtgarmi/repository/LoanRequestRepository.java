package com.eyeson.poshtgarmi.repository;

import com.eyeson.poshtgarmi.domain.LoanRequest;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LoanRequest entity.
 */
@SuppressWarnings("unused")
public interface LoanRequestRepository extends JpaRepository<LoanRequest,Long> {

}
