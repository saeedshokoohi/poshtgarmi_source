package com.eyeson.poshtgarmi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.eyeson.poshtgarmi.domain.enumeration.LoanDurationStatus;

/**
 * A LoanDuration.
 */
@Entity
@Table(name = "loan_duration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LoanDuration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoanDurationStatus status;

    @Column(name = "agreement")
    private String agreement;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "min_member")
    private Integer minMember;

    @Column(name = "max_member")
    private Integer maxMember;

    @Column(name = "fund_seed_amount")
    private Integer fundSeedAmount;

    @Column(name = "sarresid_day")
    private Integer sarresidDay;

    @Column(name = "loan_pay_day")
    private Integer loanPayDay;

    @ManyToOne
    private Fund fund;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "loan_duration_members",
               joinColumns = @JoinColumn(name="loan_durations_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="members_id", referencedColumnName="ID"))
    private Set<Member> members = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public LoanDuration title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public LoanDuration description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LoanDurationStatus getStatus() {
        return status;
    }

    public LoanDuration status(LoanDurationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(LoanDurationStatus status) {
        this.status = status;
    }

    public String getAgreement() {
        return agreement;
    }

    public LoanDuration agreement(String agreement) {
        this.agreement = agreement;
        return this;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public LoanDuration createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public LoanDuration startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public Integer getMinMember() {
        return minMember;
    }

    public LoanDuration minMember(Integer minMember) {
        this.minMember = minMember;
        return this;
    }

    public void setMinMember(Integer minMember) {
        this.minMember = minMember;
    }

    public Integer getMaxMember() {
        return maxMember;
    }

    public LoanDuration maxMember(Integer maxMember) {
        this.maxMember = maxMember;
        return this;
    }

    public void setMaxMember(Integer maxMember) {
        this.maxMember = maxMember;
    }

    public Integer getFundSeedAmount() {
        return fundSeedAmount;
    }

    public LoanDuration fundSeedAmount(Integer fundSeedAmount) {
        this.fundSeedAmount = fundSeedAmount;
        return this;
    }

    public void setFundSeedAmount(Integer fundSeedAmount) {
        this.fundSeedAmount = fundSeedAmount;
    }

    public Integer getSarresidDay() {
        return sarresidDay;
    }

    public LoanDuration sarresidDay(Integer sarresidDay) {
        this.sarresidDay = sarresidDay;
        return this;
    }

    public void setSarresidDay(Integer sarresidDay) {
        this.sarresidDay = sarresidDay;
    }

    public Integer getLoanPayDay() {
        return loanPayDay;
    }

    public LoanDuration loanPayDay(Integer loanPayDay) {
        this.loanPayDay = loanPayDay;
        return this;
    }

    public void setLoanPayDay(Integer loanPayDay) {
        this.loanPayDay = loanPayDay;
    }

    public Fund getFund() {
        return fund;
    }

    public LoanDuration fund(Fund fund) {
        this.fund = fund;
        return this;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public LoanDuration members(Set<Member> members) {
        this.members = members;
        return this;
    }

    public LoanDuration addMembers(Member member) {
        members.add(member);
        member.getLoanDurations().add(this);
        return this;
    }

    public LoanDuration removeMembers(Member member) {
        members.remove(member);
        member.getLoanDurations().remove(this);
        return this;
    }

    public void setMembers(Set<Member> members) {
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
        LoanDuration loanDuration = (LoanDuration) o;
        if(loanDuration.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, loanDuration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LoanDuration{" +
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
