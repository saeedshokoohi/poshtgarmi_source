package com.eyeson.poshtgarmi.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.eyeson.poshtgarmi.domain.enumeration.PaymentType;
import com.eyeson.poshtgarmi.domain.enumeration.PaymentStatus;

/**
 * A DTO for the Payment entity.
 */
public class PaymentDTO implements Serializable {

    private Long id;

    private PaymentType type;

    private ZonedDateTime createDate;

    private Integer amount;

    private String transactionInfo;

    private PaymentStatus status;

    private String description;


    private Long loanDurationIterationId;
    
    private Long memberId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }
    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public String getTransactionInfo() {
        return transactionInfo;
    }

    public void setTransactionInfo(String transactionInfo) {
        this.transactionInfo = transactionInfo;
    }
    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        PaymentDTO paymentDTO = (PaymentDTO) o;

        if ( ! Objects.equals(id, paymentDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", createDate='" + createDate + "'" +
            ", amount='" + amount + "'" +
            ", transactionInfo='" + transactionInfo + "'" +
            ", status='" + status + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
