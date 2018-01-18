package com.vyka.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.vyka.domain.enumeration.ActivityType;

/**
 * A DTO for the OrderActivity entity.
 */
public class OrderActivityDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal amount;

    private String currencyCode;

    @NotNull
    private ActivityType activityType;

    @NotNull
    private Instant createdDate;

    private Instant updatedDate;

    private BigDecimal amountLocalCurrency;

    private Long packageOrderId;

    private Long settlementId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public BigDecimal getAmountLocalCurrency() {
        return amountLocalCurrency;
    }

    public void setAmountLocalCurrency(BigDecimal amountLocalCurrency) {
        this.amountLocalCurrency = amountLocalCurrency;
    }

    public Long getPackageOrderId() {
        return packageOrderId;
    }

    public void setPackageOrderId(Long packageOrderId) {
        this.packageOrderId = packageOrderId;
    }

    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderActivityDTO orderActivityDTO = (OrderActivityDTO) o;
        if(orderActivityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderActivityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderActivityDTO{" +
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
