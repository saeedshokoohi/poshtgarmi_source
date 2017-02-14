package com.eyeson.poshtgarmi.service;

import com.eyeson.poshtgarmi.domain.LoanDurationIteration;
import com.eyeson.poshtgarmi.repository.LoanDurationIterationRepository;
import com.eyeson.poshtgarmi.service.dto.LoanDurationIterationDTO;
import com.eyeson.poshtgarmi.service.mapper.LoanDurationIterationMapper;
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
 * Service Implementation for managing LoanDurationIteration.
 */
@Service
@Transactional
public class LoanDurationIterationService {

    private final Logger log = LoggerFactory.getLogger(LoanDurationIterationService.class);
    
    @Inject
    private LoanDurationIterationRepository loanDurationIterationRepository;

    @Inject
    private LoanDurationIterationMapper loanDurationIterationMapper;

    /**
     * Save a loanDurationIteration.
     *
     * @param loanDurationIterationDTO the entity to save
     * @return the persisted entity
     */
    public LoanDurationIterationDTO save(LoanDurationIterationDTO loanDurationIterationDTO) {
        log.debug("Request to save LoanDurationIteration : {}", loanDurationIterationDTO);
        LoanDurationIteration loanDurationIteration = loanDurationIterationMapper.loanDurationIterationDTOToLoanDurationIteration(loanDurationIterationDTO);
        loanDurationIteration = loanDurationIterationRepository.save(loanDurationIteration);
        LoanDurationIterationDTO result = loanDurationIterationMapper.loanDurationIterationToLoanDurationIterationDTO(loanDurationIteration);
        return result;
    }

    /**
     *  Get all the loanDurationIterations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<LoanDurationIterationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoanDurationIterations");
        Page<LoanDurationIteration> result = loanDurationIterationRepository.findAll(pageable);
        return result.map(loanDurationIteration -> loanDurationIterationMapper.loanDurationIterationToLoanDurationIterationDTO(loanDurationIteration));
    }

    /**
     *  Get one loanDurationIteration by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LoanDurationIterationDTO findOne(Long id) {
        log.debug("Request to get LoanDurationIteration : {}", id);
        LoanDurationIteration loanDurationIteration = loanDurationIterationRepository.findOneWithEagerRelationships(id);
        LoanDurationIterationDTO loanDurationIterationDTO = loanDurationIterationMapper.loanDurationIterationToLoanDurationIterationDTO(loanDurationIteration);
        return loanDurationIterationDTO;
    }

    /**
     *  Delete the  loanDurationIteration by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LoanDurationIteration : {}", id);
        loanDurationIterationRepository.delete(id);
    }
}
