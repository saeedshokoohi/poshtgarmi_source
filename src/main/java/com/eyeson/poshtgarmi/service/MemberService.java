package com.eyeson.poshtgarmi.service;

import com.eyeson.poshtgarmi.domain.Member;
import com.eyeson.poshtgarmi.repository.MemberRepository;
import com.eyeson.poshtgarmi.service.dto.MemberDTO;
import com.eyeson.poshtgarmi.service.mapper.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Member.
 */
@Service
@Transactional
public class MemberService {

    private final Logger log = LoggerFactory.getLogger(MemberService.class);

    @Inject
    private MemberRepository memberRepository;

    @Inject
    private MemberMapper memberMapper;

    /**
     * Save a member.
     *
     * @param memberDTO the entity to save
     * @return the persisted entity
     */
    public MemberDTO save(MemberDTO memberDTO) {
        log.debug("Request to save Member : {}", memberDTO);
        Member member = memberMapper.memberDTOToMember(memberDTO);
        member = memberRepository.save(member);
        MemberDTO result = memberMapper.memberToMemberDTO(member);
        return result;
    }

    /**
     *  Get all the members.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MemberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Members");
        Page<Member> result = memberRepository.findAll(pageable);
        return result.map(member -> memberMapper.memberToMemberDTO(member));
    }

    /**
     *  Get one member by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MemberDTO findOne(Long id) {
        log.debug("Request to get Member : {}", id);
        Member member = memberRepository.findOne(id);
        MemberDTO memberDTO = memberMapper.memberToMemberDTO(member);
        return memberDTO;
    }

    /**
     *  Delete the  member by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Member : {}", id);
        memberRepository.delete(id);
    }

    public Member findByUserId(Long userid) {
        return memberRepository.findOneByUserid(userid);
    }
}
