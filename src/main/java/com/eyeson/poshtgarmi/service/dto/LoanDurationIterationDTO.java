package com.eyeson.poshtgarmi.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.eyeson.poshtgarmi.domain.enumeration.LoanDurationIterationStatus;

/**
 * A DTO for the LoanDurationIteration entity.
 */
public class LoanDurationIterationDTO implements Serializable {

    private Long id;

    private String title;

    private ZonedDateTime createDate;

    private Integer iterationIndex;

    private LoanDurationIterationStatus status;


    private Long loanDurationId;
    
    private Set<PaymentDTO> payments = new HashSet<>();

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
    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }
    public Integer getIterationIndex() {
        return iterationIndex;
    }

    public void setIterationIndex(Integer iterationIndex) {
        this.iterationIndex = iterationIndex;
    }
    public LoanDurationIterationStatus getStatus() {
        return status;
    }

    public void setStatus(LoanDurationIterationStatus status) {
        this.status = status;
    }

    public Long getLoanDurationId() {
        return loanDurationId;
    }

    public void setLoanDurationId(Long loanDurationId) {
        this.loanDurationId = loanDurationId;
    }

    public Set<PaymentDTO> getPayments() {
        return payments;
    }

    public void setPayments(Set<PaymentDTO> payments) {
        this.payments = payments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoanDurationIterationDTO loanDurationIterationDTO = (LoanDurationIterationDTO) o;

        if ( ! Objects.equals(id, loanDurationIterationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LoanDurationIterationDTO{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", createDate='" + createDate + "'" +
            ", iterationIndex='" + iterationIndex + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
