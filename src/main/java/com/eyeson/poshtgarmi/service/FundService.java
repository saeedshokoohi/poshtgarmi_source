package com.eyeson.poshtgarmi.service;

import com.eyeson.poshtgarmi.domain.Fund;
import com.eyeson.poshtgarmi.domain.LoanDuration;
import com.eyeson.poshtgarmi.domain.LoanDurationIteration;
import com.eyeson.poshtgarmi.repository.FundRepository;
import com.eyeson.poshtgarmi.repository.extended.FundExtendedRepository;
import com.eyeson.poshtgarmi.service.dto.FundDTO;
import com.eyeson.poshtgarmi.service.dto.FundStatDTO;
import com.eyeson.poshtgarmi.service.dto.MemberDTO;
import com.eyeson.poshtgarmi.service.mapper.FundMapper;
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
 * Service Implementation for managing Fund.
 */
@Service
@Transactional
public class FundService {

    private final Logger log = LoggerFactory.getLogger(FundService.class);

    @Inject
    private FundRepository fundRepository;

    @Inject
    private FundMapper fundMapper;

    /**
     * Save a fund.
     *
     * @param fundDTO the entity to save
     * @return the persisted entity
     */
    public FundDTO save(FundDTO fundDTO) {
        log.debug("Request to save Fund : {}", fundDTO);
        Fund fund = fundMapper.fundDTOToFund(fundDTO);
        fund = fundRepository.save(fund);
        FundDTO result = fundMapper.fundToFundDTO(fund);
        return result;
    }

    /**
     *  Get all the funds.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FundDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Funds");
        Page<Fund> result = fundRepository.findAll(pageable);
        return result.map(fund -> fundMapper.fundToFundDTO(fund));

    }

    /**
     *  Get one fund by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public FundDTO findOne(Long id) {
        log.debug("Request to get Fund : {}", id);
        Fund fund = fundRepository.findOneWithEagerRelationships(id);
        FundDTO fundDTO = fundMapper.fundToFundDTO(fund);
        return fundDTO;
    }

    /**
     *  Delete the  fund by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Fund : {}", id);
        fundRepository.delete(id);
    }

  @Inject
    FundExtendedRepository fundExtendedRepository;
    public FundStatDTO findFundStatByMember(Long memberid) {
        FundStatDTO retFund=new FundStatDTO();
        List<Fund> funds = fundExtendedRepository.getFundByMemberId(memberid);
        if(funds.size()>0)
        {
            Fund f=funds.get(0);
            f.setMembers(f.getMembers());
            retFund=makeFundStatDto(f);

        }
        return retFund;

    }
    public int getFundMember(Long id) {
        Fund f = fundRepository.getOne(id);
        if(f!=null)return f.getMembers().size();
        return 0;
    }

    public FundStatDTO makeFundStatDto(Fund f)
    {
        FundStatDTO retFund=new FundStatDTO();
        retFund.setTitle(f.getTitle());
        retFund.setMembersCount(f.getMembers().size());
        List<LoanDuration> durations = fundExtendedRepository.getLoanDurationByFundId(f.getId());
        retFund.setFundCredit(fundExtendedRepository.getFundCreditByFundId(f.getId()));
        FundDTO fundDto = fundMapper.fundToFundDTO(f);
         retFund.setMembers(fundDto.getMembers());
        if(durations.size()>0)
        {
            retFund.setCurrentDuration(durations.get(0));
         List<LoanDurationIteration> iterations=fundExtendedRepository.getCurrentIterationByDuration(retFund.getCurrentDuration().getId());
            if(iterations.size()>0)
            {
                retFund.setCurrentIteration(iterations.get(0));
                 retFund.setAllMemberHasPaid(true);
                for(MemberDTO m:retFund.getMembers())
                {
                    m.setHasPaid(fundExtendedRepository.hasMemberPaid(m.getId(),retFund.getCurrentIteration().getId()));
                    if(!m.isHasPaid())
                        retFund.setAllMemberHasPaid(false);
                }
            }

        }

        return  retFund;
    }
}
