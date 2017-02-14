package com.eyeson.poshtgarmi.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the LoanRequest entity.
 */
public class LoanRequestDTO implements Serializable {

    private Long id;

    private ZonedDateTime createDate;

    private String description;

    private String status;


    private Long loanDurationIterationId;
    
    private Long memberId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getLoanDurationIterationId() {
        return loanDurationIterationId;
    }

    public void setLoanDurationIterationId(Long loanDurationIterationId) {
        this.loanDurationIterationId = loanDurationIterationId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoanRequestDTO loanRequestDTO = (LoanRequestDTO) o;

        if ( ! Objects.equals(id, loanRequestDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LoanRequestDTO{" +
            "id=" + id +
            ", createDate='" + createDate + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
