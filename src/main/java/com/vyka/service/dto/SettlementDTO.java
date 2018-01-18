package com.vyka.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.vyka.domain.enumeration.SettlementType;
import com.vyka.domain.enumeration.SettlementStatus;

/**
 * A DTO for the Settlement entity.
 */
public class SettlementDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private SettlementType settlementType;

    @NotNull
    @Max(value = 3)
    private Integer attempts;

    @NotNull
    private SettlementStatus status;

    @NotNull
    private Instant settlementDate;

    private String transactionId;

    private String processorResponseCode;

    private String processorResponseText;

    private Long paymentId;

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

    public SettlementType getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(SettlementType settlementType) {
        this.settlementType = settlementType;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public SettlementStatus getStatus() {
        return status;
    }

    public void setStatus(SettlementStatus status) {
        this.status = status;
    }

    public Instant getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Instant settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProcessorResponseCode() {
        return processorResponseCode;
    }

    public void setProcessorResponseCode(String processorResponseCode) {
        this.processorResponseCode = processorResponseCode;
    }

    public String getProcessorResponseText() {
        return processorResponseText;
    }

    public void setProcessorResponseText(String processorResponseText) {
        this.processorResponseText = processorResponseText;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SettlementDTO settlementDTO = (SettlementDTO) o;
        if(settlementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), settlementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SettlementDTO{" +
            "id=" + getId() +
            ", amount='" + getAmount() + "'" +
            ", settlementType='" + getSettlementType() + "'" +
            ", attempts='" + getAttempts() + "'" +
            ", status='" + getStatus() + "'" +
            ", settlementDate='" + getSettlementDate() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", processorResponseCode='" + getProcessorResponseCode() + "'" +
            ", processorResponseText='" + getProcessorResponseText() + "'" +
            "}";
    }
}
