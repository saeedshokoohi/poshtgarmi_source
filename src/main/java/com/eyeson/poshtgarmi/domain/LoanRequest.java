package com.eyeson.poshtgarmi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A LoanRequest.
 */
@Entity
@Table(name = "loan_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LoanRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @ManyToOne
    private LoanDurationIteration loanDurationIteration;

    @ManyToOne
    private Member member;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public LoanRequest createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public LoanRequest description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public LoanRequest status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoanDurationIteration getLoanDurationIteration() {
        return loanDurationIteration;
    }

    public LoanRequest loanDurationIteration(LoanDurationIteration loanDurationIteration) {
        this.loanDurationIteration = loanDurationIteration;
        return this;
    }

    public void setLoanDurationIteration(LoanDurationIteration loanDurationIteration) {
        this.loanDurationIteration = loanDurationIteration;
    }

    public Member getMember() {
        return member;
    }

    public LoanRequest member(Member member) {
        this.member = member;
        return this;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoanRequest loanRequest = (LoanRequest) o;
        if(loanRequest.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, loanRequest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LoanRequest{" +
            "id=" + id +
            ", createDate='" + createDate + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
