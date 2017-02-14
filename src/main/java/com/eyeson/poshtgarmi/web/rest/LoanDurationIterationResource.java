package com.eyeson.poshtgarmi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyeson.poshtgarmi.service.LoanDurationIterationService;
import com.eyeson.poshtgarmi.web.rest.util.HeaderUtil;
import com.eyeson.poshtgarmi.web.rest.util.PaginationUtil;
import com.eyeson.poshtgarmi.service.dto.LoanDurationIterationDTO;
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
 * REST controller for managing LoanDurationIteration.
 */
@RestController
@RequestMapping("/api")
public class LoanDurationIterationResource {

    private final Logger log = LoggerFactory.getLogger(LoanDurationIterationResource.class);
        
    @Inject
    private LoanDurationIterationService loanDurationIterationService;

    /**
     * POST  /loan-duration-iterations : Create a new loanDurationIteration.
     *
     * @param loanDurationIterationDTO the loanDurationIterationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loanDurationIterationDTO, or with status 400 (Bad Request) if the loanDurationIteration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/loan-duration-iterations")
    @Timed
    public ResponseEntity<LoanDurationIterationDTO> createLoanDurationIteration(@RequestBody LoanDurationIterationDTO loanDurationIterationDTO) throws URISyntaxException {
        log.debug("REST request to save LoanDurationIteration : {}", loanDurationIterationDTO);
        if (loanDurationIterationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("loanDurationIteration", "idexists", "A new loanDurationIteration cannot already have an ID")).body(null);
        }
        LoanDurationIterationDTO result = loanDurationIterationService.save(loanDurationIterationDTO);
        return ResponseEntity.created(new URI("/api/loan-duration-iterations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("loanDurationIteration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /loan-duration-iterations : Updates an existing loanDurationIteration.
     *
     * @param loanDurationIterationDTO the loanDurationIterationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loanDurationIterationDTO,
     * or with status 400 (Bad Request) if the loanDurationIterationDTO is not valid,
     * or with status 500 (Internal Server Error) if the loanDurationIterationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/loan-duration-iterations")
    @Timed
    public ResponseEntity<LoanDurationIterationDTO> updateLoanDurationIteration(@RequestBody LoanDurationIterationDTO loanDurationIterationDTO) throws URISyntaxException {
        log.debug("REST request to update LoanDurationIteration : {}", loanDurationIterationDTO);
        if (loanDurationIterationDTO.getId() == null) {
            return createLoanDurationIteration(loanDurationIterationDTO);
        }
        LoanDurationIterationDTO result = loanDurationIterationService.save(loanDurationIterationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("loanDurationIteration", loanDurationIterationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /loan-duration-iterations : get all the loanDurationIterations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of loanDurationIterations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/loan-duration-iterations")
    @Timed
    public ResponseEntity<List<LoanDurationIterationDTO>> getAllLoanDurationIterations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of LoanDurationIterations");
        Page<LoanDurationIterationDTO> page = loanDurationIterationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/loan-duration-iterations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /loan-duration-iterations/:id : get the "id" loanDurationIteration.
     *
     * @param id the id of the loanDurationIterationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loanDurationIterationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/loan-duration-iterations/{id}")
    @Timed
    public ResponseEntity<LoanDurationIterationDTO> getLoanDurationIteration(@PathVariable Long id) {
        log.debug("REST request to get LoanDurationIteration : {}", id);
        LoanDurationIterationDTO loanDurationIterationDTO = loanDurationIterationService.findOne(id);
        return Optional.ofNullable(loanDurationIterationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /loan-duration-iterations/:id : delete the "id" loanDurationIteration.
     *
     * @param id the id of the loanDurationIterationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/loan-duration-iterations/{id}")
    @Timed
    public ResponseEntity<Void> deleteLoanDurationIteration(@PathVariable Long id) {
        log.debug("REST request to delete LoanDurationIteration : {}", id);
        loanDurationIterationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("loanDurationIteration", id.toString())).build();
    }

}
