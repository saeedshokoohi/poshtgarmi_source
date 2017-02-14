package com.eyeson.poshtgarmi.service.mapper;

import com.eyeson.poshtgarmi.domain.*;
import com.eyeson.poshtgarmi.service.dto.FundDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Fund and its DTO FundDTO.
 */
@Mapper(componentModel = "spring", uses = {MemberMapper.class, })
public interface FundMapper {

    FundDTO fundToFundDTO(Fund fund);

    List<FundDTO> fundsToFundDTOs(List<Fund> funds);

    Fund fundDTOToFund(FundDTO fundDTO);

    List<Fund> fundDTOsToFunds(List<FundDTO> fundDTOs);

    default Member memberFromId(Long id) {
        if (id == null) {
            return null;
        }
        Member member = new Member();
        member.setId(id);
        return member;
    }
}
