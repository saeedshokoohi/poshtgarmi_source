package com.eyeson.poshtgarmi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.eyeson.poshtgarmi.domain.enumeration.MemberType;

/**
 * A Member.
 */
@Entity
@Table(name = "member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "userid")
    private Long userid;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type")
    private MemberType memberType;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "national_code")
    private String nationalCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "ibn_number")
    private String ibnNumber;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "bank_code")
    private String bankCode;

    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Fund> funds = new HashSet<>();

    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LoanDuration> loanDurations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Member name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserid() {
        return userid;
    }

    public Member userid(Long userid) {
        this.userid = userid;
        return this;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public Member memberType(MemberType memberType) {
        this.memberType = memberType;
        return this;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public String getLastName() {
        return lastName;
    }

    public Member lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public Member nationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
        return this;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Member phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIbnNumber() {
        return ibnNumber;
    }

    public Member ibnNumber(String ibnNumber) {
        this.ibnNumber = ibnNumber;
        return this;
    }

    public void setIbnNumber(String ibnNumber) {
        this.ibnNumber = ibnNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Member cardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Member accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public Member bankCode(String bankCode) {
        this.bankCode = bankCode;
        return this;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Set<Fund> getFunds() {
        return funds;
    }

    public Member funds(Set<Fund> funds) {
        this.funds = funds;
        return this;
    }

    public Member addFunds(Fund fund) {
        funds.add(fund);
        fund.getMembers().add(this);
        return this;
    }

    public Member removeFunds(Fund fund) {
        funds.remove(fund);
        fund.getMembers().remove(this);
        return this;
    }

    public void setFunds(Set<Fund> funds) {
        this.funds = funds;
    }

    public Set<LoanDuration> getLoanDurations() {
        return loanDurations;
    }

    public Member loanDurations(Set<LoanDuration> loanDurations) {
        this.loanDurations = loanDurations;
        return this;
    }

    public Member addLoanDurations(LoanDuration loanDuration) {
        loanDurations.add(loanDuration);
        loanDuration.getMembers().add(this);
        return this;
    }

    public Member removeLoanDurations(LoanDuration loanDuration) {
        loanDurations.remove(loanDuration);
        loanDuration.getMembers().remove(this);
        return this;
    }

    public void setLoanDurations(Set<LoanDuration> loanDurations) {
        this.loanDurations = loanDurations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        if(member.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", userid='" + userid + "'" +
            ", memberType='" + memberType + "'" +
            ", lastName='" + lastName + "'" +
            ", nationalCode='" + nationalCode + "'" +
            ", phoneNumber='" + phoneNumber + "'" +
            ", ibnNumber='" + ibnNumber + "'" +
            ", cardNumber='" + cardNumber + "'" +
            ", accountNumber='" + accountNumber + "'" +
            ", bankCode='" + bankCode + "'" +
            '}';
    }
}
