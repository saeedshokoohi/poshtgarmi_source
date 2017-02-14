package com.eyeson.poshtgarmi.web.rest;

import com.eyeson.poshtgarmi.PoshtgarmiApp;

import com.eyeson.poshtgarmi.domain.LoanDuration;
import com.eyeson.poshtgarmi.repository.LoanDurationRepository;
import com.eyeson.poshtgarmi.service.LoanDurationService;
import com.eyeson.poshtgarmi.service.dto.LoanDurationDTO;
import com.eyeson.poshtgarmi.service.mapper.LoanDurationMapper;

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

import com.eyeson.poshtgarmi.domain.enumeration.LoanDurationStatus;
/**
 * Test class for the LoanDurationResource REST controller.
 *
 * @see LoanDurationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PoshtgarmiApp.class)
public class LoanDurationResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final LoanDurationStatus DEFAULT_STATUS = LoanDurationStatus.ACTIVE;
    private static final LoanDurationStatus UPDATED_STATUS = LoanDurationStatus.INACTIVE;

    private static final String DEFAULT_AGREEMENT = "AAAAA";
    private static final String UPDATED_AGREEMENT = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_CREATE_DATE);

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_START_DATE);

    private static final Integer DEFAULT_MIN_MEMBER = 1;
    private static final Integer UPDATED_MIN_MEMBER = 2;

    private static final Integer DEFAULT_MAX_MEMBER = 1;
    private static final Integer UPDATED_MAX_MEMBER = 2;

    private static final Integer DEFAULT_FUND_SEED_AMOUNT = 1;
    private static final Integer UPDATED_FUND_SEED_AMOUNT = 2;

    private static final Integer DEFAULT_SARRESID_DAY = 1;
    private static final Integer UPDATED_SARRESID_DAY = 2;

    private static final Integer DEFAULT_LOAN_PAY_DAY = 1;
    private static final Integer UPDATED_LOAN_PAY_DAY = 2;

    @Inject
    private LoanDurationRepository loanDurationRepository;

    @Inject
    private LoanDurationMapper loanDurationMapper;

    @Inject
    private LoanDurationService loanDurationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLoanDurationMockMvc;

    private LoanDuration loanDuration;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LoanDurationResource loanDurationResource = new LoanDurationResource();
        ReflectionTestUtils.setField(loanDurationResource, "loanDurationService", loanDurationService);
        this.restLoanDurationMockMvc = MockMvcBuilders.standaloneSetup(loanDurationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanDuration createEntity(EntityManager em) {
        LoanDuration loanDuration = new LoanDuration()
                .title(DEFAULT_TITLE)
                .description(DEFAULT_DESCRIPTION)
                .status(DEFAULT_STATUS)
                .agreement(DEFAULT_AGREEMENT)
                .createDate(DEFAULT_CREATE_DATE)
                .startDate(DEFAULT_START_DATE)
                .minMember(DEFAULT_MIN_MEMBER)
                .maxMember(DEFAULT_MAX_MEMBER)
                .fundSeedAmount(DEFAULT_FUND_SEED_AMOUNT)
                .sarresidDay(DEFAULT_SARRESID_DAY)
                .loanPayDay(DEFAULT_LOAN_PAY_DAY);
        return loanDuration;
    }

    @Before
    public void initTest() {
        loanDuration = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoanDuration() throws Exception {
        int databaseSizeBeforeCreate = loanDurationRepository.findAll().size();

        // Create the LoanDuration
        LoanDurationDTO loanDurationDTO = loanDurationMapper.loanDurationToLoanDurationDTO(loanDuration);

        restLoanDurationMockMvc.perform(post("/api/loan-durations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loanDurationDTO)))
                .andExpect(status().isCreated());

        // Validate the LoanDuration in the database
        List<LoanDuration> loanDurations = loanDurationRepository.findAll();
        assertThat(loanDurations).hasSize(databaseSizeBeforeCreate + 1);
        LoanDuration testLoanDuration = loanDurations.get(loanDurations.size() - 1);
        assertThat(testLoanDuration.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testLoanDuration.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLoanDuration.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLoanDuration.getAgreement()).isEqualTo(DEFAULT_AGREEMENT);
        assertThat(testLoanDuration.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testLoanDuration.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testLoanDuration.getMinMember()).isEqualTo(DEFAULT_MIN_MEMBER);
        assertThat(testLoanDuration.getMaxMember()).isEqualTo(DEFAULT_MAX_MEMBER);
        assertThat(testLoanDuration.getFundSeedAmount()).isEqualTo(DEFAULT_FUND_SEED_AMOUNT);
        assertThat(testLoanDuration.getSarresidDay()).isEqualTo(DEFAULT_SARRESID_DAY);
        assertThat(testLoanDuration.getLoanPayDay()).isEqualTo(DEFAULT_LOAN_PAY_DAY);
    }

    @Test
    @Transactional
    public void getAllLoanDurations() throws Exception {
        // Initialize the database
        loanDurationRepository.saveAndFlush(loanDuration);

        // Get all the loanDurations
        restLoanDurationMockMvc.perform(get("/api/loan-durations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(loanDuration.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].agreement").value(hasItem(DEFAULT_AGREEMENT.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].minMember").value(hasItem(DEFAULT_MIN_MEMBER)))
                .andExpect(jsonPath("$.[*].maxMember").value(hasItem(DEFAULT_MAX_MEMBER)))
                .andExpect(jsonPath("$.[*].fundSeedAmount").value(hasItem(DEFAULT_FUND_SEED_AMOUNT)))
                .andExpect(jsonPath("$.[*].sarresidDay").value(hasItem(DEFAULT_SARRESID_DAY)))
                .andExpect(jsonPath("$.[*].loanPayDay").value(hasItem(DEFAULT_LOAN_PAY_DAY)));
    }

    @Test
    @Transactional
    public void getLoanDuration() throws Exception {
        // Initialize the database
        loanDurationRepository.saveAndFlush(loanDuration);

        // Get the loanDuration
        restLoanDurationMockMvc.perform(get("/api/loan-durations/{id}", loanDuration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loanDuration.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.agreement").value(DEFAULT_AGREEMENT.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.minMember").value(DEFAULT_MIN_MEMBER))
            .andExpect(jsonPath("$.maxMember").value(DEFAULT_MAX_MEMBER))
            .andExpect(jsonPath("$.fundSeedAmount").value(DEFAULT_FUND_SEED_AMOUNT))
            .andExpect(jsonPath("$.sarresidDay").value(DEFAULT_SARRESID_DAY))
            .andExpect(jsonPath("$.loanPayDay").value(DEFAULT_LOAN_PAY_DAY));
    }

    @Test
    @Transactional
    public void getNonExistingLoanDuration() throws Exception {
        // Get the loanDuration
        restLoanDurationMockMvc.perform(get("/api/loan-durations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoanDuration() throws Exception {
        // Initialize the database
        loanDurationRepository.saveAndFlush(loanDuration);
        int databaseSizeBeforeUpdate = loanDurationRepository.findAll().size();

        // Update the loanDuration
        LoanDuration updatedLoanDuration = loanDurationRepository.findOne(loanDuration.getId());
        updatedLoanDuration
                .title(UPDATED_TITLE)
                .description(UPDATED_DESCRIPTION)
                .status(UPDATED_STATUS)
                .agreement(UPDATED_AGREEMENT)
                .createDate(UPDATED_CREATE_DATE)
                .startDate(UPDATED_START_DATE)
                .minMember(UPDATED_MIN_MEMBER)
                .maxMember(UPDATED_MAX_MEMBER)
                .fundSeedAmount(UPDATED_FUND_SEED_AMOUNT)
                .sarresidDay(UPDATED_SARRESID_DAY)
                .loanPayDay(UPDATED_LOAN_PAY_DAY);
        LoanDurationDTO loanDurationDTO = loanDurationMapper.loanDurationToLoanDurationDTO(updatedLoanDuration);

        restLoanDurationMockMvc.perform(put("/api/loan-durations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(loanDurationDTO)))
                .andExpect(status().isOk());

        // Validate the LoanDuration in the database
        List<LoanDuration> loanDurations = loanDurationRepository.findAll();
        assertThat(loanDurations).hasSize(databaseSizeBeforeUpdate);
        LoanDuration testLoanDuration = loanDurations.get(loanDurations.size() - 1);
        assertThat(testLoanDuration.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testLoanDuration.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLoanDuration.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoanDuration.getAgreement()).isEqualTo(UPDATED_AGREEMENT);
        assertThat(testLoanDuration.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testLoanDuration.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testLoanDuration.getMinMember()).isEqualTo(UPDATED_MIN_MEMBER);
        assertThat(testLoanDuration.getMaxMember()).isEqualTo(UPDATED_MAX_MEMBER);
        assertThat(testLoanDuration.getFundSeedAmount()).isEqualTo(UPDATED_FUND_SEED_AMOUNT);
        assertThat(testLoanDuration.getSarresidDay()).isEqualTo(UPDATED_SARRESID_DAY);
        assertThat(testLoanDuration.getLoanPayDay()).isEqualTo(UPDATED_LOAN_PAY_DAY);
    }

    @Test
    @Transactional
    public void deleteLoanDuration() throws Exception {
        // Initialize the database
        loanDurationRepository.saveAndFlush(loanDuration);
        int databaseSizeBeforeDelete = loanDurationRepository.findAll().size();

        // Get the loanDuration
        restLoanDurationMockMvc.perform(delete("/api/loan-durations/{id}", loanDuration.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LoanDuration> loanDurations = loanDurationRepository.findAll();
        assertThat(loanDurations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
