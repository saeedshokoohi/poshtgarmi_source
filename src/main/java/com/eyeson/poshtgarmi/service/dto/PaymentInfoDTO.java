package com.eyeson.poshtgarmi.service.dto;

import com.eyeson.poshtgarmi.domain.LoanDuration;
import com.eyeson.poshtgarmi.domain.LoanDurationIteration;
import com.eyeson.poshtgarmi.domain.enumeration.FundStatus;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Fund entity.
 */
public class PaymentInfoDTO implements Serializable {


    private MemberDTO fromMember;
    private MemberDTO toMember;
    private Integer amount;
    private String username;
    private String password;
    private int type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFromMember(MemberDTO fromMember) {
        this.fromMember = fromMember;
    }

    public MemberDTO getFromMember() {
        return fromMember;
    }

    public void setToMember(MemberDTO toMember) {
        this.toMember = toMember;
    }

    public MemberDTO getToMember() {
        return toMember;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
