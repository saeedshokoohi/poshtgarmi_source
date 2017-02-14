package com.eyeson.poshtgarmi.service.mapper;

import com.eyeson.poshtgarmi.domain.*;
import com.eyeson.poshtgarmi.service.dto.LoanDurationIterationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LoanDurationIteration and its DTO LoanDurationIterationDTO.
 */
@Mapper(componentModel = "spring", uses = {PaymentMapper.class, })
public interface LoanDurationIterationMapper {

    @Mapping(source = "loanDuration.id", target = "loanDurationId")
    LoanDurationIterationDTO loanDurationIterationToLoanDurationIterationDTO(LoanDurationIteration loanDurationIteration);

    List<LoanDurationIterationDTO> loanDurationIterationsToLoanDurationIterationDTOs(List<LoanDurationIteration> loanDurationIterations);

    @Mapping(source = "loanDurationId", target = "loanDuration")
    LoanDurationIteration loanDurationIterationDTOToLoanDurationIteration(LoanDurationIterationDTO loanDurationIterationDTO);

    List<LoanDurationIteration> loanDurationIterationDTOsToLoanDurationIterations(List<LoanDurationIterationDTO> loanDurationIterationDTOs);

    default LoanDuration loanDurationFromId(Long id) {
        if (id == null) {
            return null;
        }
        LoanDuration loanDuration = new LoanDuration();
        loanDuration.setId(id);
        return loanDuration;
    }

    default Payment paymentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }
}
