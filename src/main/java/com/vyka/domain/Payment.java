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

import com.vyka.domain.enumeration.PaymentVia;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "payment")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "total_amount", precision=10, scale=2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "local_currency_code")
    private String localCurrencyCode;

    @Column(name = "settlement_currency_code")
    private String settlementCurrencyCode;

    @Column(name = "captured_amount", precision=10, scale=2)
    private BigDecimal capturedAmount;

    @Column(name = "canceled_amount", precision=10, scale=2)
    private BigDecimal canceledAmount;

    @Column(name = "refund_amount", precision=10, scale=2)
    private BigDecimal refundAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_via")
    private PaymentVia paymentVia;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @OneToOne
    @JoinColumn(unique = true)
    private PackageOrder packageOrder;

    @OneToMany(mappedBy = "payment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Settlement> settlements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Payment totalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getLocalCurrencyCode() {
        return localCurrencyCode;
    }

    public Payment localCurrencyCode(String localCurrencyCode) {
        this.localCurrencyCode = localCurrencyCode;
        return this;
    }

    public void setLocalCurrencyCode(String localCurrencyCode) {
        this.localCurrencyCode = localCurrencyCode;
    }

    public String getSettlementCurrencyCode() {
        return settlementCurrencyCode;
    }

    public Payment settlementCurrencyCode(String settlementCurrencyCode) {
        this.settlementCurrencyCode = settlementCurrencyCode;
        return this;
    }

    public void setSettlementCurrencyCode(String settlementCurrencyCode) {
        this.settlementCurrencyCode = settlementCurrencyCode;
    }

    public BigDecimal getCapturedAmount() {
        return capturedAmount;
    }

    public Payment capturedAmount(BigDecimal capturedAmount) {
        this.capturedAmount = capturedAmount;
        return this;
    }

    public void setCapturedAmount(BigDecimal capturedAmount) {
        this.capturedAmount = capturedAmount;
    }

    public BigDecimal getCanceledAmount() {
        return canceledAmount;
    }

    public Payment canceledAmount(BigDecimal canceledAmount) {
        this.canceledAmount = canceledAmount;
        return this;
    }

    public void setCanceledAmount(BigDecimal canceledAmount) {
        this.canceledAmount = canceledAmount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public Payment refundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
        return this;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public PaymentVia getPaymentVia() {
        return paymentVia;
    }

    public Payment paymentVia(PaymentVia paymentVia) {
        this.paymentVia = paymentVia;
        return this;
    }

    public void setPaymentVia(PaymentVia paymentVia) {
        this.paymentVia = paymentVia;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Payment createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public Payment updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public PackageOrder getPackageOrder() {
        return packageOrder;
    }

    public Payment packageOrder(PackageOrder packageOrder) {
        this.packageOrder = packageOrder;
        return this;
    }

    public void setPackageOrder(PackageOrder packageOrder) {
        this.packageOrder = packageOrder;
    }

    public Set<Settlement> getSettlements() {
        return settlements;
    }

    public Payment settlements(Set<Settlement> settlements) {
        this.settlements = settlements;
        return this;
    }

    public Payment addSettlement(Settlement settlement) {
        this.settlements.add(settlement);
        settlement.setPayment(this);
        return this;
    }

    public Payment removeSettlement(Settlement settlement) {
        this.settlements.remove(settlement);
        settlement.setPayment(null);
        return this;
    }

    public void setSettlements(Set<Settlement> settlements) {
        this.settlements = settlements;
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
        Payment payment = (Payment) o;
        if (payment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), payment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Payment{" +
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
