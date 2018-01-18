package com.vyka.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.vyka.domain.enumeration.ActivityType;

/**
 * A OrderActivity.
 */
@Entity
@Table(name = "order_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderactivity")
public class OrderActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @Column(name = "currency_code")
    private String currencyCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "amount_local_currency", precision=10, scale=2)
    private BigDecimal amountLocalCurrency;

    @ManyToOne
    private PackageOrder packageOrder;

    @OneToOne
    @JoinColumn(unique = true)
    private Settlement settlement;

    @OneToMany(mappedBy = "orderActivity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ScheduleActivity> scheduleActivities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public OrderActivity amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public OrderActivity currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public OrderActivity activityType(ActivityType activityType) {
        this.activityType = activityType;
        return this;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public OrderActivity createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public OrderActivity updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public BigDecimal getAmountLocalCurrency() {
        return amountLocalCurrency;
    }

    public OrderActivity amountLocalCurrency(BigDecimal amountLocalCurrency) {
        this.amountLocalCurrency = amountLocalCurrency;
        return this;
    }

    public void setAmountLocalCurrency(BigDecimal amountLocalCurrency) {
        this.amountLocalCurrency = amountLocalCurrency;
    }

    public PackageOrder getPackageOrder() {
        return packageOrder;
    }

    public OrderActivity packageOrder(PackageOrder packageOrder) {
        this.packageOrder = packageOrder;
        return this;
    }

    public void setPackageOrder(PackageOrder packageOrder) {
        this.packageOrder = packageOrder;
    }

    public Settlement getSettlement() {
        return settlement;
    }

    public OrderActivity settlement(Settlement settlement) {
        this.settlement = settlement;
        return this;
    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }

    public Set<ScheduleActivity> getScheduleActivities() {
        return scheduleActivities;
    }

    public OrderActivity scheduleActivities(Set<ScheduleActivity> scheduleActivities) {
        this.scheduleActivities = scheduleActivities;
        return this;
    }

    public OrderActivity addScheduleActivity(ScheduleActivity scheduleActivity) {
        this.scheduleActivities.add(scheduleActivity);
        scheduleActivity.setOrderActivity(this);
        return this;
    }

    public OrderActivity removeScheduleActivity(ScheduleActivity scheduleActivity) {
        this.scheduleActivities.remove(scheduleActivity);
        scheduleActivity.setOrderActivity(null);
        return this;
    }

    public void setScheduleActivities(Set<ScheduleActivity> scheduleActivities) {
        this.scheduleActivities = scheduleActivities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderActivity orderActivity = (OrderActivity) o;
        if (orderActivity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderActivity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderActivity{" +
            "id=" + getId() +
            ", amount='" + getAmount() + "'" +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", activityType='" + getActivityType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", amountLocalCurrency='" + getAmountLocalCurrency() + "'" +
            "}";
    }
}
