package com.eyeson.poshtgarmi.service.mapper;

import com.eyeson.poshtgarmi.domain.*;
import com.eyeson.poshtgarmi.service.dto.PaymentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Payment and its DTO PaymentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentMapper {

    @Mapping(source = "loanDurationIteration.id", target = "loanDurationIterationId")
    @Mapping(source = "member.id", target = "memberId")
    PaymentDTO paymentToPaymentDTO(Payment payment);

    List<PaymentDTO> paymentsToPaymentDTOs(List<Payment> payments);

    @Mapping(source = "loanDurationIterationId", target = "loanDurationIteration")
    @Mapping(source = "memberId", target = "member")
    @Mapping(target = "loanDurationIterations", ignore = true)
    Payment paymentDTOToPayment(PaymentDTO paymentDTO);

    List<Payment> paymentDTOsToPayments(List<PaymentDTO> paymentDTOs);

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
