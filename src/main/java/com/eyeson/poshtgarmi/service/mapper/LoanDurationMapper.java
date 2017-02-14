package com.eyeson.poshtgarmi.service.mapper;

import com.eyeson.poshtgarmi.domain.*;
import com.eyeson.poshtgarmi.service.dto.LoanDurationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity LoanDuration and its DTO LoanDurationDTO.
 */
@Mapper(componentModel = "spring", uses = {MemberMapper.class, })
public interface LoanDurationMapper {

    @Mapping(source = "fund.id", target = "fundId")
    LoanDurationDTO loanDurationToLoanDurationDTO(LoanDuration loanDuration);

    List<LoanDurationDTO> loanDurationsToLoanDurationDTOs(List<LoanDuration> loanDurations);

    @Mapping(source = "fundId", target = "fund")
    LoanDuration loanDurationDTOToLoanDuration(LoanDurationDTO loanDurationDTO);

    List<LoanDuration> loanDurationDTOsToLoanDurations(List<LoanDurationDTO> loanDurationDTOs);

    default Fund fundFromId(Long id) {
        if (id == null) {
            return null;
        }
        Fund fund = new Fund();
        fund.setId(id);
        return fund;
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
