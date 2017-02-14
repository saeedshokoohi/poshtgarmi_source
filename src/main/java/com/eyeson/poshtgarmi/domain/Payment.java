package com.eyeson.poshtgarmi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.eyeson.poshtgarmi.domain.enumeration.PaymentType;

import com.eyeson.poshtgarmi.domain.enumeration.PaymentStatus;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PaymentType type;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "transaction_info")
    private String transactionInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private LoanDurationIteration loanDurationIteration;

    @ManyToOne
    private Member member;

    @ManyToMany(mappedBy = "payments")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LoanDurationIteration> loanDurationIterations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentType getType() {
        return type;
    }

    public Payment type(PaymentType type) {
        this.type = type;
        return this;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public Payment createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public Payment amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTransactionInfo() {
        return transactionInfo;
    }

    public Payment transactionInfo(String transactionInfo) {
        this.transactionInfo = transactionInfo;
        return this;
    }

    public void setTransactionInfo(String transactionInfo) {
        this.transactionInfo = transactionInfo;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Payment status(PaymentStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public Payment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LoanDurationIteration getLoanDurationIteration() {
        return loanDurationIteration;
    }

    public Payment loanDurationIteration(LoanDurationIteration loanDurationIteration) {
        this.loanDurationIteration = loanDurationIteration;
        return this;
    }

    public void setLoanDurationIteration(LoanDurationIteration loanDurationIteration) {
        this.loanDurationIteration = loanDurationIteration;
    }

    public Member getMember() {
        return member;
    }

    public Payment member(Member member) {
        this.member = member;
        return this;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Set<LoanDurationIteration> getLoanDurationIterations() {
        return loanDurationIterations;
    }

    public Payment loanDurationIterations(Set<LoanDurationIteration> loanDurationIterations) {
        this.loanDurationIterations = loanDurationIterations;
        return this;
    }

    public Payment addLoanDurationIteration(LoanDurationIteration loanDurationIteration) {
        loanDurationIterations.add(loanDurationIteration);
        loanDurationIteration.getPayments().add(this);
        return this;
    }

    public Payment removeLoanDurationIteration(LoanDurationIteration loanDurationIteration) {
        loanDurationIterations.remove(loanDurationIteration);
        loanDurationIteration.getPayments().remove(this);
        return this;
    }

    public void setLoanDurationIterations(Set<LoanDurationIteration> loanDurationIterations) {
        this.loanDurationIterations = loanDurationIterations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Payment payment = (Payment) o;
        if(payment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Payment{" +
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
