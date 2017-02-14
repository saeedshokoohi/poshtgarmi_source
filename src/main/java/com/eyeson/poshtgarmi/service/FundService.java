package com.eyeson.poshtgarmi.service;

import com.eyeson.poshtgarmi.domain.Fund;
import com.eyeson.poshtgarmi.repository.FundRepository;
import com.eyeson.poshtgarmi.service.dto.FundDTO;
import com.eyeson.poshtgarmi.service.mapper.FundMapper;
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
}
