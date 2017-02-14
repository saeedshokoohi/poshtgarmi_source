package com.eyeson.poshtgarmi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.eyeson.poshtgarmi.domain.enumeration.FundStatus;

/**
 * A Fund.
 */
@Entity
@Table(name = "fund")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fund implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "agreement")
    private String agreement;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FundStatus status;

    @Column(name = "type")
    private String type;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "fund_members",
               joinColumns = @JoinColumn(name="funds_id", referencedColumnName="ID"),
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

    public Fund title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Fund description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgreement() {
        return agreement;
    }

    public Fund agreement(String agreement) {
        this.agreement = agreement;
        return this;
    }

    public void setAgreement(String agreement) {
        this.agreement = agreement;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public Fund createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public FundStatus getStatus() {
        return status;
    }

    public Fund status(FundStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(FundStatus status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public Fund type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public Fund members(Set<Member> members) {
        this.members = members;
        return this;
    }

    public Fund addMembers(Member member) {
        members.add(member);
        member.getFunds().add(this);
        return this;
    }

    public Fund removeMembers(Member member) {
        members.remove(member);
        member.getFunds().remove(this);
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
        Fund fund = (Fund) o;
        if(fund.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fund.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fund{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", agreement='" + agreement + "'" +
            ", createDate='" + createDate + "'" +
            ", status='" + status + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
