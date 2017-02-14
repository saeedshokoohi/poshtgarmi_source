package com.eyeson.poshtgarmi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.eyeson.poshtgarmi.domain.enumeration.LoanDurationIterationStatus;

/**
 * A LoanDurationIteration.
 */
@Entity
@Table(name = "loan_duration_iteration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LoanDurationIteration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "iteration_index")
    private Integer iterationIndex;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoanDurationIterationStatus status;

    @ManyToOne
    private LoanDuration loanDuration;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "loan_duration_iteration_payment",
               joinColumns = @JoinColumn(name="loan_duration_iterations_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="payments_id", referencedColumnName="ID"))
    private Set<Payment> payments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public LoanDurationIteration title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public LoanDurationIteration createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Integer getIterationIndex() {
        return iterationIndex;
    }

    public LoanDurationIteration iterationIndex(Integer iterationIndex) {
        this.iterationIndex = iterationIndex;
        return this;
    }

    public void setIterationIndex(Integer iterationIndex) {
        this.iterationIndex = iterationIndex;
    }

    public LoanDurationIterationStatus getStatus() {
        return status;
    }

    public LoanDurationIteration status(LoanDurationIterationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(LoanDurationIterationStatus status) {
        this.status = status;
    }

    public LoanDuration getLoanDuration() {
        return loanDuration;
    }

    public LoanDurationIteration loanDuration(LoanDuration loanDuration) {
        this.loanDuration = loanDuration;
        return this;
    }

    public void setLoanDuration(LoanDuration loanDuration) {
        this.loanDuration = loanDuration;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public LoanDurationIteration payments(Set<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public LoanDurationIteration addPayment(Payment payment) {
        payments.add(payment);
        payment.getLoanDurationIterations().add(this);
        return this;
    }

    public LoanDurationIteration removePayment(Payment payment) {
        payments.remove(payment);
        payment.getLoanDurationIterations().remove(this);
        return this;
    }

    public void setPayments(Set<Payment> payments) {
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
        LoanDurationIteration loanDurationIteration = (LoanDurationIteration) o;
        if(loanDurationIteration.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, loanDurationIteration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LoanDurationIteration{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", createDate='" + createDate + "'" +
            ", iterationIndex='" + iterationIndex + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
