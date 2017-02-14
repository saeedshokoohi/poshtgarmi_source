package com.eyeson.poshtgarmi.repository;

import com.eyeson.poshtgarmi.domain.LoanDurationIteration;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the LoanDurationIteration entity.
 */
@SuppressWarnings("unused")
public interface LoanDurationIterationRepository extends JpaRepository<LoanDurationIteration,Long> {

    @Query("select distinct loanDurationIteration from LoanDurationIteration loanDurationIteration left join fetch loanDurationIteration.payments")
    List<LoanDurationIteration> findAllWithEagerRelationships();

    @Query("select loanDurationIteration from LoanDurationIteration loanDurationIteration left join fetch loanDurationIteration.payments where loanDurationIteration.id =:id")
    LoanDurationIteration findOneWithEagerRelationships(@Param("id") Long id);

}
