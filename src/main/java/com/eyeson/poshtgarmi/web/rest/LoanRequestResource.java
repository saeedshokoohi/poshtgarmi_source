package com.eyeson.poshtgarmi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyeson.poshtgarmi.service.LoanRequestService;
import com.eyeson.poshtgarmi.web.rest.util.HeaderUtil;
import com.eyeson.poshtgarmi.web.rest.util.PaginationUtil;
import com.eyeson.poshtgarmi.service.dto.LoanRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing LoanRequest.
 */
@RestController
@RequestMapping("/api")
public class LoanRequestResource {

    private final Logger log = LoggerFactory.getLogger(LoanRequestResource.class);
        
    @Inject
    private LoanRequestService loanRequestService;

    /**
     * POST  /loan-requests : Create a new loanRequest.
     *
     * @param loanRequestDTO the loanRequestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loanRequestDTO, or with status 400 (Bad Request) if the loanRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/loan-requests")
    @Timed
    public ResponseEntity<LoanRequestDTO> createLoanRequest(@RequestBody LoanRequestDTO loanRequestDTO) throws URISyntaxException {
        log.debug("REST request to save LoanRequest : {}", loanRequestDTO);
        if (loanRequestDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("loanRequest", "idexists", "A new loanRequest cannot already have an ID")).body(null);
        }
        LoanRequestDTO result = loanRequestService.save(loanRequestDTO);
        return ResponseEntity.created(new URI("/api/loan-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("loanRequest", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /loan-requests : Updates an existing loanRequest.
     *
     * @param loanRequestDTO the loanRequestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loanRequestDTO,
     * or with status 400 (Bad Request) if the loanRequestDTO is not valid,
     * or with status 500 (Internal Server Error) if the loanRequestDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/loan-requests")
    @Timed
    public ResponseEntity<LoanRequestDTO> updateLoanRequest(@RequestBody LoanRequestDTO loanRequestDTO) throws URISyntaxException {
        log.debug("REST request to update LoanRequest : {}", loanRequestDTO);
        if (loanRequestDTO.getId() == null) {
            return createLoanRequest(loanRequestDTO);
        }
        LoanRequestDTO result = loanRequestService.save(loanRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("loanRequest", loanRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /loan-requests : get all the loanRequests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of loanRequests in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/loan-requests")
    @Timed
    public ResponseEntity<List<LoanRequestDTO>> getAllLoanRequests(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of LoanRequests");
        Page<LoanRequestDTO> page = loanRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/loan-requests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /loan-requests/:id : get the "id" loanRequest.
     *
     * @param id the id of the loanRequestDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loanRequestDTO, or with status 404 (Not Found)
     */
    @GetMapping("/loan-requests/{id}")
    @Timed
    public ResponseEntity<LoanRequestDTO> getLoanRequest(@PathVariable Long id) {
        log.debug("REST request to get LoanRequest : {}", id);
        LoanRequestDTO loanRequestDTO = loanRequestService.findOne(id);
        return Optional.ofNullable(loanRequestDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /loan-requests/:id : delete the "id" loanRequest.
     *
     * @param id the id of the loanRequestDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/loan-requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteLoanRequest(@PathVariable Long id) {
        log.debug("REST request to delete LoanRequest : {}", id);
        loanRequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("loanRequest", id.toString())).build();
    }

}
