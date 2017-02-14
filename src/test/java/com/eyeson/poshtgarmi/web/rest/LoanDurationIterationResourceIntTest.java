package com.eyeson.poshtgarmi.web.rest;

import com.eyeson.poshtgarmi.PoshtgarmiApp;

import com.eyeson.poshtgarmi.domain.LoanDurationIteration;
import com.eyeson.poshtgarmi.repository.LoanDurationIterationRepository;
import com.eyeson.poshtgarmi.service.LoanDurationIterationService;
import com.eyeson.poshtgarmi.service.dto.LoanDurationIterationDTO;
import com.eyeson.poshtgarmi.service.mapper.LoanDurationIterationMapper;

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

import com.eyeson.poshtgarmi.domain.enumeration.LoanDurationIterationStatus;
/**
 * Test class for the LoanDurationIterationResource REST controller.
 *
 * @see LoanDurationIterationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PoshtgarmiApp.class)
public class LoanDurationIterationResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_CREATE_DATE);

    private static final Integer DEFAULT_ITERATION_INDEX = 1;
    private static final Integer UPDATED_ITERATION_INDEX = 2;

    private static final LoanDurationIterationStatus DEFAULT_STATUS = LoanDurationIterationStatus.ACTIVE;
    private static final LoanDurationIterationStatus UPDATED_STATUS = LoanDurationIterationStatus.INACTIVE;

    @Inject
    private LoanDurationIterationRepository loanDurationIterationRepository;

    @Inject
    private LoanDurationIterationMapper loanDurationIterationMapper;

    @Inject
    private LoanDurationIterationService loanDurationIterationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLoanDurationIterationMockMvc;

    private LoanDurationIteration loanDurationIteration;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LoanDurationIterationResource loanDurationIterationResource = new LoanDurationIterationResource();
        ReflectionTestUtils.setField(loanDurationIterationResource, "loanDurationIterationService", loanDurationIterationService);
        this.restLoanDurationIterationMockMvc = MockMvcBuilders.standaloneSetup(loanDurationIterationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanDurationIteration createEntity(EntityManager em) {
        LoanDurationIteration loanDurationIteration = new LoanDurationIteration()
                .title(DEFAULT_TITLE)
                .createDate(DEFAULT_CREATE_DATE)
                .iterationIndex(DEFAULT_ITERATION_INDEX)
                .status(DEFAULT_STATUS);
        return loanDurationIteration;
    }

    @Before
    public void initTest() {
        loanDurationIteration = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoanDurationIteration() throws Exception {
        int databaseSizeBeforeCreate = loanDurationIterationRepository.findAll().size();

        // Create the LoanDurationIteration
        LoanDurationIterationDTO loanDurationIterationDTO = loanDurationIterationMapper.loanDurationIterationToLoanDurationIterationDTO(loanDurationIteration);

        restLoanDurationIterationMockMvc.perform(post("/api/loan-duration-iterations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loanDurationIterationDTO)))
                .andExpect(status().isCreated());

        // Validate the LoanDurationIteration in the database
        List<LoanDurationIteration> loanDurationIterations = loanDurationIterationRepository.findAll();
        assertThat(loanDurationIterations).hasSize(databaseSizeBeforeCreate + 1);
        LoanDurationIteration testLoanDurationIteration = loanDurationIterations.get(loanDurationIterations.size() - 1);
        assertThat(testLoanDurationIteration.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testLoanDurationIteration.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testLoanDurationIteration.getIterationIndex()).isEqualTo(DEFAULT_ITERATION_INDEX);
        assertThat(testLoanDurationIteration.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void getAllLoanDurationIterations() throws Exception {
        // Initialize the database
        loanDurationIterationRepository.saveAndFlush(loanDurationIteration);

        // Get all the loanDurationIterations
        restLoanDurationIterationMockMvc.perform(get("/api/loan-duration-iterations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(loanDurationIteration.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].iterationIndex").value(hasItem(DEFAULT_ITERATION_INDEX)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getLoanDurationIteration() throws Exception {
        // Initialize the database
        loanDurationIterationRepository.saveAndFlush(loanDurationIteration);

        // Get the loanDurationIteration
        restLoanDurationIterationMockMvc.perform(get("/api/loan-duration-iterations/{id}", loanDurationIteration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loanDurationIteration.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.iterationIndex").value(DEFAULT_ITERATION_INDEX))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLoanDurationIteration() throws Exception {
        // Get the loanDurationIteration
        restLoanDurationIterationMockMvc.perform(get("/api/loan-duration-iterations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoanDurationIteration() throws Exception {
        // Initialize the database
        loanDurationIterationRepository.saveAndFlush(loanDurationIteration);
        int databaseSizeBeforeUpdate = loanDurationIterationRepository.findAll().size();

        // Update the loanDurationIteration
        LoanDurationIteration updatedLoanDurationIteration = loanDurationIterationRepository.findOne(loanDurationIteration.getId());
        updatedLoanDurationIteration
                .title(UPDATED_TITLE)
                .createDate(UPDATED_CREATE_DATE)
                .iterationIndex(UPDATED_ITERATION_INDEX)
                .status(UPDATED_STATUS);
        LoanDurationIterationDTO loanDurationIterationDTO = loanDurationIterationMapper.loanDurationIterationToLoanDurationIterationDTO(updatedLoanDurationIteration);

        restLoanDurationIterationMockMvc.perform(put("/api/loan-duration-iterations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loanDurationIterationDTO)))
                .andExpect(status().isOk());

        // Validate the LoanDurationIteration in the database
        List<LoanDurationIteration> loanDurationIterations = loanDurationIterationRepository.findAll();
        assertThat(loanDurationIterations).hasSize(databaseSizeBeforeUpdate);
        LoanDurationIteration testLoanDurationIteration = loanDurationIterations.get(loanDurationIterations.size() - 1);
        assertThat(testLoanDurationIteration.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLoanDurationIteration.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testLoanDurationIteration.getIterationIndex()).isEqualTo(UPDATED_ITERATION_INDEX);
        assertThat(testLoanDurationIteration.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteLoanDurationIteration() throws Exception {
        // Initialize the database
        loanDurationIterationRepository.saveAndFlush(loanDurationIteration);
        int databaseSizeBeforeDelete = loanDurationIterationRepository.findAll().size();

        // Get the loanDurationIteration
        restLoanDurationIterationMockMvc.perform(delete("/api/loan-duration-iterations/{id}", loanDurationIteration.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LoanDurationIteration> loanDurationIterations = loanDurationIterationRepository.findAll();
        assertThat(loanDurationIterations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
