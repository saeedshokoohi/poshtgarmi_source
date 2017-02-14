package com.eyeson.poshtgarmi.service;

import com.eyeson.poshtgarmi.domain.LoanRequest;
import com.eyeson.poshtgarmi.repository.LoanRequestRepository;
import com.eyeson.poshtgarmi.service.dto.LoanRequestDTO;
import com.eyeson.poshtgarmi.service.mapper.LoanRequestMapper;
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
 * Service Implementation for managing LoanRequest.
 */
@Service
@Transactional
public class LoanRequestService {

    private final Logger log = LoggerFactory.getLogger(LoanRequestService.class);
    
    @Inject
    private LoanRequestRepository loanRequestRepository;

    @Inject
    private LoanRequestMapper loanRequestMapper;

    /**
     * Save a loanRequest.
     *
     * @param loanRequestDTO the entity to save
     * @return the persisted entity
     */
    public LoanRequestDTO save(LoanRequestDTO loanRequestDTO) {
        log.debug("Request to save LoanRequest : {}", loanRequestDTO);
        LoanRequest loanRequest = loanRequestMapper.loanRequestDTOToLoanRequest(loanRequestDTO);
        loanRequest = loanRequestRepository.save(loanRequest);
        LoanRequestDTO result = loanRequestMapper.loanRequestToLoanRequestDTO(loanRequest);
        return result;
    }

    /**
     *  Get all the loanRequests.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<LoanRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoanRequests");
        Page<LoanRequest> result = loanRequestRepository.findAll(pageable);
        return result.map(loanRequest -> loanRequestMapper.loanRequestToLoanRequestDTO(loanRequest));
    }

    /**
     *  Get one loanRequest by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LoanRequestDTO findOne(Long id) {
        log.debug("Request to get LoanRequest : {}", id);
        LoanRequest loanRequest = loanRequestRepository.findOne(id);
        LoanRequestDTO loanRequestDTO = loanRequestMapper.loanRequestToLoanRequestDTO(loanRequest);
        return loanRequestDTO;
    }

    /**
     *  Delete the  loanRequest by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LoanRequest : {}", id);
        loanRequestRepository.delete(id);
    }
}
