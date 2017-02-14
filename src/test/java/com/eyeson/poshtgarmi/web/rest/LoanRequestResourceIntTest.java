package com.eyeson.poshtgarmi.web.rest;

import com.eyeson.poshtgarmi.PoshtgarmiApp;

import com.eyeson.poshtgarmi.domain.LoanRequest;
import com.eyeson.poshtgarmi.repository.LoanRequestRepository;
import com.eyeson.poshtgarmi.service.LoanRequestService;
import com.eyeson.poshtgarmi.service.dto.LoanRequestDTO;
import com.eyeson.poshtgarmi.service.mapper.LoanRequestMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LoanRequestResource REST controller.
 *
 * @see LoanRequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PoshtgarmiApp.class)
public class LoanRequestResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_CREATE_DATE);

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final String DEFAULT_STATUS = "AAAAA";
    private static final String UPDATED_STATUS = "BBBBB";

    @Inject
    private LoanRequestRepository loanRequestRepository;

    @Inject
    private LoanRequestMapper loanRequestMapper;

    @Inject
    private LoanRequestService loanRequestService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLoanRequestMockMvc;

    private LoanRequest loanRequest;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LoanRequestResource loanRequestResource = new LoanRequestResource();
        ReflectionTestUtils.setField(loanRequestResource, "loanRequestService", loanRequestService);
        this.restLoanRequestMockMvc = MockMvcBuilders.standaloneSetup(loanRequestResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanRequest createEntity(EntityManager em) {
        LoanRequest loanRequest = new LoanRequest()
                .createDate(DEFAULT_CREATE_DATE)
                .description(DEFAULT_DESCRIPTION)
                .status(DEFAULT_STATUS);
        return loanRequest;
    }

    @Before
    public void initTest() {
        loanRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoanRequest() throws Exception {
        int databaseSizeBeforeCreate = loanRequestRepository.findAll().size();

        // Create the LoanRequest
        LoanRequestDTO loanRequestDTO = loanRequestMapper.loanRequestToLoanRequestDTO(loanRequest);

        restLoanRequestMockMvc.perform(post("/api/loan-requests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loanRequestDTO)))
                .andExpect(status().isCreated());

        // Validate the LoanRequest in the database
        List<LoanRequest> loanRequests = loanRequestRepository.findAll();
        assertThat(loanRequests).hasSize(databaseSizeBeforeCreate + 1);
        LoanRequest testLoanRequest = loanRequests.get(loanRequests.size() - 1);
        assertThat(testLoanRequest.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testLoanRequest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLoanRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllLoanRequests() throws Exception {
        // Initialize the database
        loanRequestRepository.saveAndFlush(loanRequest);

        // Get all the loanRequests
        restLoanRequestMockMvc.perform(get("/api/loan-requests?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(loanRequest.getId().intValue())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getLoanRequest() throws Exception {
        // Initialize the database
        loanRequestRepository.saveAndFlush(loanRequest);

        // Get the loanRequest
        restLoanRequestMockMvc.perform(get("/api/loan-requests/{id}", loanRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loanRequest.getId().intValue()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLoanRequest() throws Exception {
        // Get the loanRequest
        restLoanRequestMockMvc.perform(get("/api/loan-requests/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoanRequest() throws Exception {
        // Initialize the database
        loanRequestRepository.saveAndFlush(loanRequest);
        int databaseSizeBeforeUpdate = loanRequestRepository.findAll().size();

        // Update the loanRequest
        LoanRequest updatedLoanRequest = loanRequestRepository.findOne(loanRequest.getId());
        updatedLoanRequest
                .createDate(UPDATED_CREATE_DATE)
                .description(UPDATED_DESCRIPTION)
                .status(UPDATED_STATUS);
        LoanRequestDTO loanRequestDTO = loanRequestMapper.loanRequestToLoanRequestDTO(updatedLoanRequest);

        restLoanRequestMockMvc.perform(put("/api/loan-requests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loanRequestDTO)))
                .andExpect(status().isOk());

        // Validate the LoanRequest in the database
        List<LoanRequest> loanRequests = loanRequestRepository.findAll();
        assertThat(loanRequests).hasSize(databaseSizeBeforeUpdate);
        LoanRequest testLoanRequest = loanRequests.get(loanRequests.size() - 1);
        assertThat(testLoanRequest.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testLoanRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLoanRequest.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteLoanRequest() throws Exception {
        // Initialize the database
        loanRequestRepository.saveAndFlush(loanRequest);
        int databaseSizeBeforeDelete = loanRequestRepository.findAll().size();

        // Get the loanRequest
        restLoanRequestMockMvc.perform(delete("/api/loan-requests/{id}", loanRequest.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LoanRequest> loanRequests = loanRequestRepository.findAll();
        assertThat(loanRequests).hasSize(databaseSizeBeforeDelete - 1);
    }
}
