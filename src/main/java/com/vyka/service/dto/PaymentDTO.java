package com.vyka.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.vyka.domain.enumeration.PaymentVia;

/**
 * A DTO for the Payment entity.
 */
public class PaymentDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal totalAmount;

    private String localCurrencyCode;

    private String settlementCurrencyCode;

    private BigDecimal capturedAmount;

    private BigDecimal canceledAmount;

    private BigDecimal refundAmount;

    private PaymentVia paymentVia;

    @NotNull
    private Instant createdDate;

    private Instant updatedDate;

    private Long packageOrderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getLocalCurrencyCode() {
        return localCurrencyCode;
    }

    public void setLocalCurrencyCode(String localCurrencyCode) {
        this.localCurrencyCode = localCurrencyCode;
    }

    public String getSettlementCurrencyCode() {
        return settlementCurrencyCode;
    }

    public void setSettlementCurrencyCode(String settlementCurrencyCode) {
        this.settlementCurrencyCode = settlementCurrencyCode;
    }

    public BigDecimal getCapturedAmount() {
        return capturedAmount;
    }

    public void setCapturedAmount(BigDecimal capturedAmount) {
        this.capturedAmount = capturedAmount;
    }

    public BigDecimal getCanceledAmount() {
        return canceledAmount;
    }

    public void setCanceledAmount(BigDecimal canceledAmount) {
        this.canceledAmount = canceledAmount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public PaymentVia getPaymentVia() {
        return paymentVia;
    }

    public void setPaymentVia(PaymentVia paymentVia) {
        this.paymentVia = paymentVia;
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

    public Long getPackageOrderId() {
        return packageOrderId;
    }

    public void setPackageOrderId(Long packageOrderId) {
        this.packageOrderId = packageOrderId;
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
        if(paymentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paymentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + getId() +
            ", totalAmount='" + getTotalAmount() + "'" +
            ", localCurrencyCode='" + getLocalCurrencyCode() + "'" +
            ", settlementCurrencyCode='" + getSettlementCurrencyCode() + "'" +
            ", capturedAmount='" + getCapturedAmount() + "'" +
            ", canceledAmount='" + getCanceledAmount() + "'" +
            ", refundAmount='" + getRefundAmount() + "'" +
            ", paymentVia='" + getPaymentVia() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
