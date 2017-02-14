package com.eyeson.poshtgarmi.repository;

import com.eyeson.poshtgarmi.domain.LoanDuration;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the LoanDuration entity.
 */
@SuppressWarnings("unused")
public interface LoanDurationRepository extends JpaRepository<LoanDuration,Long> {

    @Query("select distinct loanDuration from LoanDuration loanDuration left join fetch loanDuration.members")
    List<LoanDuration> findAllWithEagerRelationships();

    @Query("select loanDuration from LoanDuration loanDuration left join fetch loanDuration.members where loanDuration.id =:id")
    LoanDuration findOneWithEagerRelationships(@Param("id") Long id);

}
