package com.eyeson.poshtgarmi.service;

import com.eyeson.poshtgarmi.domain.LoanDuration;
import com.eyeson.poshtgarmi.repository.LoanDurationRepository;
import com.eyeson.poshtgarmi.service.dto.LoanDurationDTO;
import com.eyeson.poshtgarmi.service.mapper.LoanDurationMapper;
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
 * Service Implementation for managing LoanDuration.
 */
@Service
@Transactional
public class LoanDurationService {

    private final Logger log = LoggerFactory.getLogger(LoanDurationService.class);
    
    @Inject
    private LoanDurationRepository loanDurationRepository;

    @Inject
    private LoanDurationMapper loanDurationMapper;

    /**
     * Save a loanDuration.
     *
     * @param loanDurationDTO the entity to save
     * @return the persisted entity
     */
    public LoanDurationDTO save(LoanDurationDTO loanDurationDTO) {
        log.debug("Request to save LoanDuration : {}", loanDurationDTO);
        LoanDuration loanDuration = loanDurationMapper.loanDurationDTOToLoanDuration(loanDurationDTO);
        loanDuration = loanDurationRepository.save(loanDuration);
        LoanDurationDTO result = loanDurationMapper.loanDurationToLoanDurationDTO(loanDuration);
        return result;
    }

    /**
     *  Get all the loanDurations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<LoanDurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoanDurations");
        Page<LoanDuration> result = loanDurationRepository.findAll(pageable);
        return result.map(loanDuration -> loanDurationMapper.loanDurationToLoanDurationDTO(loanDuration));
    }

    /**
     *  Get one loanDuration by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LoanDurationDTO findOne(Long id) {
        log.debug("Request to get LoanDuration : {}", id);
        LoanDuration loanDuration = loanDurationRepository.findOneWithEagerRelationships(id);
        LoanDurationDTO loanDurationDTO = loanDurationMapper.loanDurationToLoanDurationDTO(loanDuration);
        return loanDurationDTO;
    }

    /**
     *  Delete the  loanDuration by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LoanDuration : {}", id);
        loanDurationRepository.delete(id);
    }
}
