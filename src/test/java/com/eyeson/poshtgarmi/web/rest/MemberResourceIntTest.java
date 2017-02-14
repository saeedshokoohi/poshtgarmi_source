package com.eyeson.poshtgarmi.web.rest;

import com.eyeson.poshtgarmi.PoshtgarmiApp;

import com.eyeson.poshtgarmi.domain.Member;
import com.eyeson.poshtgarmi.repository.MemberRepository;
import com.eyeson.poshtgarmi.service.MemberService;
import com.eyeson.poshtgarmi.service.dto.MemberDTO;
import com.eyeson.poshtgarmi.service.mapper.MemberMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.eyeson.poshtgarmi.domain.enumeration.MemberType;
/**
 * Test class for the MemberResource REST controller.
 *
 * @see MemberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PoshtgarmiApp.class)
public class MemberResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Long DEFAULT_USERID = 1L;
    private static final Long UPDATED_USERID = 2L;

    private static final MemberType DEFAULT_MEMBER_TYPE = MemberType.MANAGER;
    private static final MemberType UPDATED_MEMBER_TYPE = MemberType.ORDINARY;

    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";

    private static final String DEFAULT_NATIONAL_CODE = "AAAAA";
    private static final String UPDATED_NATIONAL_CODE = "BBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBB";

    private static final String DEFAULT_IBN_NUMBER = "AAAAA";
    private static final String UPDATED_IBN_NUMBER = "BBBBB";

    private static final String DEFAULT_CARD_NUMBER = "AAAAA";
    private static final String UPDATED_CARD_NUMBER = "BBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBB";

    private static final String DEFAULT_BANK_CODE = "AAAAA";
    private static final String UPDATED_BANK_CODE = "BBBBB";

    @Inject
    private MemberRepository memberRepository;

    @Inject
    private MemberMapper memberMapper;

    @Inject
    private MemberService memberService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMemberMockMvc;

    private Member member;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MemberResource memberResource = new MemberResource();
        ReflectionTestUtils.setField(memberResource, "memberService", memberService);
        this.restMemberMockMvc = MockMvcBuilders.standaloneSetup(memberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createEntity(EntityManager em) {
        Member member = new Member()
                .name(DEFAULT_NAME)
                .userid(DEFAULT_USERID)
                .memberType(DEFAULT_MEMBER_TYPE)
                .lastName(DEFAULT_LAST_NAME)
                .nationalCode(DEFAULT_NATIONAL_CODE)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .ibnNumber(DEFAULT_IBN_NUMBER)
                .cardNumber(DEFAULT_CARD_NUMBER)
                .accountNumber(DEFAULT_ACCOUNT_NUMBER)
                .bankCode(DEFAULT_BANK_CODE);
        return member;
    }

    @Before
    public void initTest() {
        member = createEntity(em);
    }

    @Test
    @Transactional
    public void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member
        MemberDTO memberDTO = memberMapper.memberToMemberDTO(member);

        restMemberMockMvc.perform(post("/api/members")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
                .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = members.get(members.size() - 1);
        assertThat(testMember.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMember.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testMember.getMemberType()).isEqualTo(DEFAULT_MEMBER_TYPE);
        assertThat(testMember.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMember.getNationalCode()).isEqualTo(DEFAULT_NATIONAL_CODE);
        assertThat(testMember.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testMember.getIbnNumber()).isEqualTo(DEFAULT_IBN_NUMBER);
        assertThat(testMember.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testMember.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testMember.getBankCode()).isEqualTo(DEFAULT_BANK_CODE);
    }

    @Test
    @Transactional
    public void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the members
        restMemberMockMvc.perform(get("/api/members?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.intValue())))
                .andExpect(jsonPath("$.[*].memberType").value(hasItem(DEFAULT_MEMBER_TYPE.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].nationalCode").value(hasItem(DEFAULT_NATIONAL_CODE.toString())))
                .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].ibnNumber").value(hasItem(DEFAULT_IBN_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].bankCode").value(hasItem(DEFAULT_BANK_CODE.toString())));
    }

    @Test
    @Transactional
    public void getMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(member.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.intValue()))
            .andExpect(jsonPath("$.memberType").value(DEFAULT_MEMBER_TYPE.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.nationalCode").value(DEFAULT_NATIONAL_CODE.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.ibnNumber").value(DEFAULT_IBN_NUMBER.toString()))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER.toString()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.bankCode").value(DEFAULT_BANK_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = memberRepository.findOne(member.getId());
        updatedMember
                .name(UPDATED_NAME)
                .userid(UPDATED_USERID)
                .memberType(UPDATED_MEMBER_TYPE)
                .lastName(UPDATED_LAST_NAME)
                .nationalCode(UPDATED_NATIONAL_CODE)
                .phoneNumber(UPDATED_PHONE_NUMBER)
                .ibnNumber(UPDATED_IBN_NUMBER)
                .cardNumber(UPDATED_CARD_NUMBER)
                .accountNumber(UPDATED_ACCOUNT_NUMBER)
                .bankCode(UPDATED_BANK_CODE);
        MemberDTO memberDTO = memberMapper.memberToMemberDTO(updatedMember);

        restMemberMockMvc.perform(put("/api/members")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
                .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(databaseSizeBeforeUpdate);
        Member testMember = members.get(members.size() - 1);
        assertThat(testMember.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMember.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testMember.getMemberType()).isEqualTo(UPDATED_MEMBER_TYPE);
        assertThat(testMember.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMember.getNationalCode()).isEqualTo(UPDATED_NATIONAL_CODE);
        assertThat(testMember.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testMember.getIbnNumber()).isEqualTo(UPDATED_IBN_NUMBER);
        assertThat(testMember.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testMember.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testMember.getBankCode()).isEqualTo(UPDATED_BANK_CODE);
    }

    @Test
    @Transactional
    public void deleteMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);
        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Get the member
        restMemberMockMvc.perform(delete("/api/members/{id}", member.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(databaseSizeBeforeDelete - 1);
    }
}
