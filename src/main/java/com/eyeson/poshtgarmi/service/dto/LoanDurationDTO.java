package com.eyeson.poshtgarmi.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.eyeson.poshtgarmi.domain.enumeration.LoanDurationStatus;

/**
 * A DTO for the LoanDuration entity.
 */
public class LoanDurationDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private LoanDurationStatus status;

    private String agreement;

    private ZonedDateTime createDate;

    private ZonedDateTime startDate;

    private Integer minMember;

    private Integer maxMember;

    private Integer fundSeedAmount;

    private Integer sarresidDay;

    private Integer loanPayDay;


    private Long fundId;
    
    private Set<MemberDTO> members = new HashSet<>();

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
    public LoanDurationStatus getStatus() {
        return status;
    }

    public void setStatus(LoanDurationStatus status) {
        this.status = status;
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
    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }
    public Integer getMinMember() {
        return minMember;
    }

    public void setMinMember(Integer minMember) {
        this.minMember = minMember;
    }
    public Integer getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(Integer maxMember) {
        this.maxMember = maxMember;
    }
    public Integer getFundSeedAmount() {
        return fundSeedAmount;
    }

    public void setFundSeedAmount(Integer fundSeedAmount) {
        this.fundSeedAmount = fundSeedAmount;
    }
    public Integer getSarresidDay() {
        return sarresidDay;
    }

    public void setSarresidDay(Integer sarresidDay) {
        this.sarresidDay = sarresidDay;
    }
    public Integer getLoanPayDay() {
        return loanPayDay;
    }

    public void setLoanPayDay(Integer loanPayDay) {
        this.loanPayDay = loanPayDay;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(Long fundId) {
        this.fundId = fundId;
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

        LoanDurationDTO loanDurationDTO = (LoanDurationDTO) o;

        if ( ! Objects.equals(id, loanDurationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LoanDurationDTO{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            ", agreement='" + agreement + "'" +
            ", createDate='" + createDate + "'" +
            ", startDate='" + startDate + "'" +
            ", minMember='" + minMember + "'" +
            ", maxMember='" + maxMember + "'" +
            ", fundSeedAmount='" + fundSeedAmount + "'" +
            ", sarresidDay='" + sarresidDay + "'" +
            ", loanPayDay='" + loanPayDay + "'" +
            '}';
    }
}
