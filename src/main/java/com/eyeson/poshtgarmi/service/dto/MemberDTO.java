package com.eyeson.poshtgarmi.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.eyeson.poshtgarmi.domain.enumeration.MemberType;

/**
 * A DTO for the Member entity.
 */
public class MemberDTO implements Serializable {

    private Long id;

    private String name;

    private Long userid;

    private MemberType memberType;

    private String lastName;

    private String nationalCode;

    private String phoneNumber;

    private String ibnNumber;

    private String cardNumber;

    private String accountNumber;

    private String bankCode;
    private boolean hasPaid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getIbnNumber() {
        return ibnNumber;
    }

    public void setIbnNumber(String ibnNumber) {
        this.ibnNumber = ibnNumber;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MemberDTO memberDTO = (MemberDTO) o;

        if ( ! Objects.equals(id, memberDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
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

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public boolean isHasPaid() {
        return hasPaid;
    }
}
