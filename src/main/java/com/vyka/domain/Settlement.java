package com.vyka.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import com.vyka.domain.enumeration.SettlementType;

import com.vyka.domain.enumeration.SettlementStatus;

/**
 * A Settlement.
 */
@Entity
@Table(name = "settlement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "settlement")
public class Settlement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", precision=10, scale=2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "settlement_type", nullable = false)
    private SettlementType settlementType;

    @NotNull
    @Max(value = 3)
    @Column(name = "attempts", nullable = false)
    private Integer attempts;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SettlementStatus status;

    @NotNull
    @Column(name = "settlement_date", nullable = false)
    private Instant settlementDate;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "processor_response_code")
    private String processorResponseCode;

    @Column(name = "processor_response_text")
    private String processorResponseText;

    @ManyToOne
    private Payment payment;

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

    public Settlement amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public SettlementType getSettlementType() {
        return settlementType;
    }

    public Settlement settlementType(SettlementType settlementType) {
        this.settlementType = settlementType;
        return this;
    }

    public void setSettlementType(SettlementType settlementType) {
        this.settlementType = settlementType;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public Settlement attempts(Integer attempts) {
        this.attempts = attempts;
        return this;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public SettlementStatus getStatus() {
        return status;
    }

    public Settlement status(SettlementStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SettlementStatus status) {
        this.status = status;
    }

    public Instant getSettlementDate() {
        return settlementDate;
    }

    public Settlement settlementDate(Instant settlementDate) {
        this.settlementDate = settlementDate;
        return this;
    }

    public void setSettlementDate(Instant settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Settlement transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProcessorResponseCode() {
        return processorResponseCode;
    }

    public Settlement processorResponseCode(String processorResponseCode) {
        this.processorResponseCode = processorResponseCode;
        return this;
    }

    public void setProcessorResponseCode(String processorResponseCode) {
        this.processorResponseCode = processorResponseCode;
    }

    public String getProcessorResponseText() {
        return processorResponseText;
    }

    public Settlement processorResponseText(String processorResponseText) {
        this.processorResponseText = processorResponseText;
        return this;
    }

    public void setProcessorResponseText(String processorResponseText) {
        this.processorResponseText = processorResponseText;
    }

    public Payment getPayment() {
        return payment;
    }

    public Settlement payment(Payment payment) {
        this.payment = payment;
        return this;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
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
        Settlement settlement = (Settlement) o;
        if (settlement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), settlement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Settlement{" +
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
