package com.eyeson.poshtgarmi.web.rest;

import com.eyeson.poshtgarmi.PoshtgarmiApp;

import com.eyeson.poshtgarmi.domain.Fund;
import com.eyeson.poshtgarmi.repository.FundRepository;
import com.eyeson.poshtgarmi.service.FundService;
import com.eyeson.poshtgarmi.service.dto.FundDTO;
import com.eyeson.poshtgarmi.service.mapper.FundMapper;

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

import com.eyeson.poshtgarmi.domain.enumeration.FundStatus;
/**
 * Test class for the FundResource REST controller.
 *
 * @see FundResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PoshtgarmiApp.class)
public class FundResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final String DEFAULT_AGREEMENT = "AAAAA";
    private static final String UPDATED_AGREEMENT = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_CREATE_DATE);

    private static final FundStatus DEFAULT_STATUS = FundStatus.ACTIVE;
    private static final FundStatus UPDATED_STATUS = FundStatus.INACTIVE;

    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";

    @Inject
    private FundRepository fundRepository;

    @Inject
    private FundMapper fundMapper;

    @Inject
    private FundService fundService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFundMockMvc;

    private Fund fund;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FundResource fundResource = new FundResource();
        ReflectionTestUtils.setField(fundResource, "fundService", fundService);
        this.restFundMockMvc = MockMvcBuilders.standaloneSetup(fundResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fund createEntity(EntityManager em) {
        Fund fund = new Fund()
                .title(DEFAULT_TITLE)
                .description(DEFAULT_DESCRIPTION)
                .agreement(DEFAULT_AGREEMENT)
                .createDate(DEFAULT_CREATE_DATE)
                .status(DEFAULT_STATUS)
                .type(DEFAULT_TYPE);
        return fund;
    }

    @Before
    public void initTest() {
        fund = createEntity(em);
    }

    @Test
    @Transactional
    public void createFund() throws Exception {
        int databaseSizeBeforeCreate = fundRepository.findAll().size();

        // Create the Fund
        FundDTO fundDTO = fundMapper.fundToFundDTO(fund);

        restFundMockMvc.perform(post("/api/funds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fundDTO)))
                .andExpect(status().isCreated());

        // Validate the Fund in the database
        List<Fund> funds = fundRepository.findAll();
        assertThat(funds).hasSize(databaseSizeBeforeCreate + 1);
        Fund testFund = funds.get(funds.size() - 1);
        assertThat(testFund.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFund.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFund.getAgreement()).isEqualTo(DEFAULT_AGREEMENT);
        assertThat(testFund.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testFund.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFund.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void getAllFunds() throws Exception {
        // Initialize the database
        fundRepository.saveAndFlush(fund);

        // Get all the funds
        restFundMockMvc.perform(get("/api/funds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fund.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].agreement").value(hasItem(DEFAULT_AGREEMENT.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getFund() throws Exception {
        // Initialize the database
        fundRepository.saveAndFlush(fund);

        // Get the fund
        restFundMockMvc.perform(get("/api/funds/{id}", fund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fund.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.agreement").value(DEFAULT_AGREEMENT.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFund() throws Exception {
        // Get the fund
        restFundMockMvc.perform(get("/api/funds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFund() throws Exception {
        // Initialize the database
        fundRepository.saveAndFlush(fund);
        int databaseSizeBeforeUpdate = fundRepository.findAll().size();

        // Update the fund
        Fund updatedFund = fundRepository.findOne(fund.getId());
        updatedFund
                .title(UPDATED_TITLE)
                .description(UPDATED_DESCRIPTION)
                .agreement(UPDATED_AGREEMENT)
                .createDate(UPDATED_CREATE_DATE)
                .status(UPDATED_STATUS)
                .type(UPDATED_TYPE);
        FundDTO fundDTO = fundMapper.fundToFundDTO(updatedFund);

        restFundMockMvc.perform(put("/api/funds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fundDTO)))
                .andExpect(status().isOk());

        // Validate the Fund in the database
        List<Fund> funds = fundRepository.findAll();
        assertThat(funds).hasSize(databaseSizeBeforeUpdate);
        Fund testFund = funds.get(funds.size() - 1);
        assertThat(testFund.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFund.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFund.getAgreement()).isEqualTo(UPDATED_AGREEMENT);
        assertThat(testFund.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testFund.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFund.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteFund() throws Exception {
        // Initialize the database
        fundRepository.saveAndFlush(fund);
        int databaseSizeBeforeDelete = fundRepository.findAll().size();

        // Get the fund
        restFundMockMvc.perform(delete("/api/funds/{id}", fund.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fund> funds = fundRepository.findAll();
        assertThat(funds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
