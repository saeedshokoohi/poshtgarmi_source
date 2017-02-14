package com.eyeson.poshtgarmi.service.mapper;

import com.eyeson.poshtgarmi.domain.*;
import com.eyeson.poshtgarmi.service.dto.MessageDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Message and its DTO MessageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MessageMapper {

    @Mapping(source = "fund.id", target = "fundId")
    @Mapping(source = "member.id", target = "memberId")
    MessageDTO messageToMessageDTO(Message message);

    List<MessageDTO> messagesToMessageDTOs(List<Message> messages);

    @Mapping(source = "fundId", target = "fund")
    @Mapping(source = "memberId", target = "member")
    Message messageDTOToMessage(MessageDTO messageDTO);

    List<Message> messageDTOsToMessages(List<MessageDTO> messageDTOs);

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
