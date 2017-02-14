package com.eyeson.poshtgarmi.service.mapper;

import com.eyeson.poshtgarmi.domain.*;
import com.eyeson.poshtgarmi.service.dto.LoanRequestDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LoanRequest and its DTO LoanRequestDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LoanRequestMapper {

    @Mapping(source = "loanDurationIteration.id", target = "loanDurationIterationId")
    @Mapping(source = "member.id", target = "memberId")
    LoanRequestDTO loanRequestToLoanRequestDTO(LoanRequest loanRequest);

    List<LoanRequestDTO> loanRequestsToLoanRequestDTOs(List<LoanRequest> loanRequests);

    @Mapping(source = "loanDurationIterationId", target = "loanDurationIteration")
    @Mapping(source = "memberId", target = "member")
    LoanRequest loanRequestDTOToLoanRequest(LoanRequestDTO loanRequestDTO);

    List<LoanRequest> loanRequestDTOsToLoanRequests(List<LoanRequestDTO> loanRequestDTOs);

    default LoanDurationIteration loanDurationIterationFromId(Long id) {
        if (id == null) {
            return null;
        }
        LoanDurationIteration loanDurationIteration = new LoanDurationIteration();
        loanDurationIteration.setId(id);
        return loanDurationIteration;
    }

    default Member memberFromId(Long id) {
        if (id == null) {
            return null;
        }
        Member member = new Member();
        member.setId(id);
        return member;
    }
}
