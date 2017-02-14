package com.eyeson.poshtgarmi.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eyeson.poshtgarmi.service.LoanDurationService;
import com.eyeson.poshtgarmi.web.rest.util.HeaderUtil;
import com.eyeson.poshtgarmi.web.rest.util.PaginationUtil;
import com.eyeson.poshtgarmi.service.dto.LoanDurationDTO;
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
 * REST controller for managing LoanDuration.
 */
@RestController
@RequestMapping("/api")
public class LoanDurationResource {

    private final Logger log = LoggerFactory.getLogger(LoanDurationResource.class);
        
    @Inject
    private LoanDurationService loanDurationService;

    /**
     * POST  /loan-durations : Create a new loanDuration.
     *
     * @param loanDurationDTO the loanDurationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loanDurationDTO, or with status 400 (Bad Request) if the loanDuration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/loan-durations")
    @Timed
    public ResponseEntity<LoanDurationDTO> createLoanDuration(@RequestBody LoanDurationDTO loanDurationDTO) throws URISyntaxException {
        log.debug("REST request to save LoanDuration : {}", loanDurationDTO);
        if (loanDurationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("loanDuration", "idexists", "A new loanDuration cannot already have an ID")).body(null);
        }
        LoanDurationDTO result = loanDurationService.save(loanDurationDTO);
        return ResponseEntity.created(new URI("/api/loan-durations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("loanDuration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /loan-durations : Updates an existing loanDuration.
     *
     * @param loanDurationDTO the loanDurationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loanDurationDTO,
     * or with status 400 (Bad Request) if the loanDurationDTO is not valid,
     * or with status 500 (Internal Server Error) if the loanDurationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/loan-durations")
    @Timed
    public ResponseEntity<LoanDurationDTO> updateLoanDuration(@RequestBody LoanDurationDTO loanDurationDTO) throws URISyntaxException {
        log.debug("REST request to update LoanDuration : {}", loanDurationDTO);
        if (loanDurationDTO.getId() == null) {
            return createLoanDuration(loanDurationDTO);
        }
        LoanDurationDTO result = loanDurationService.save(loanDurationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("loanDuration", loanDurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /loan-durations : get all the loanDurations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of loanDurations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/loan-durations")
    @Timed
    public ResponseEntity<List<LoanDurationDTO>> getAllLoanDurations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of LoanDurations");
        Page<LoanDurationDTO> page = loanDurationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/loan-durations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /loan-durations/:id : get the "id" loanDuration.
     *
     * @param id the id of the loanDurationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loanDurationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/loan-durations/{id}")
    @Timed
    public ResponseEntity<LoanDurationDTO> getLoanDuration(@PathVariable Long id) {
        log.debug("REST request to get LoanDuration : {}", id);
        LoanDurationDTO loanDurationDTO = loanDurationService.findOne(id);
        return Optional.ofNullable(loanDurationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /loan-durations/:id : delete the "id" loanDuration.
     *
     * @param id the id of the loanDurationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/loan-durations/{id}")
    @Timed
    public ResponseEntity<Void> deleteLoanDuration(@PathVariable Long id) {
        log.debug("REST request to delete LoanDuration : {}", id);
        loanDurationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("loanDuration", id.toString())).build();
    }

}
