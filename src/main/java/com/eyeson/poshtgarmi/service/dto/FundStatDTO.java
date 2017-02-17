package com.eyeson.poshtgarmi.service.dto;

import com.eyeson.poshtgarmi.domain.LoanDuration;
import com.eyeson.poshtgarmi.domain.enumeration.FundStatus;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Fund entity.
 */
public class FundStatDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private String agreement;

    private ZonedDateTime createDate;

    private FundStatus status;

    private String type;


    private Set<MemberDTO> members = new HashSet<>();
    private LoanDuration currentDuration;
    private int membersCount;
    private int fundCredit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getAgreement() {
        return agreement;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }
    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }
    public FundStatus getStatus() {
        return status;
    }

    public void setStatus(FundStatus status) {
        this.status = status;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<MemberDTO> getMembers() {
        return members;
    }

    public void setMembers(Set<MemberDTO> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FundStatDTO fundDTO = (FundStatDTO) o;

        if ( ! Objects.equals(id, fundDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FundDTO{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", agreement='" + agreement + "'" +
            ", createDate='" + createDate + "'" +
            ", status='" + status + "'" +
            ", type='" + type + "'" +
            '}';
    }

    public void setCurrentDuration(LoanDuration currentDuration) {
        this.currentDuration = currentDuration;
    }

    public LoanDuration getCurrentDuration() {
        return currentDuration;
    }

    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public void setFundCredit(int fundCredit) {
        this.fundCredit = fundCredit;
    }

    public int getFundCredit() {
        return fundCredit;
    }
}
