package com.vyka.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.vyka.domain.enumeration.CreditCardType;

/**
 * A CreditCardPayment.
 */
@Entity
@Table(name = "credit_card_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "creditcardpayment")
public class CreditCardPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cc_type", nullable = false)
    private CreditCardType ccType;

    @NotNull
    @Column(name = "last_four", nullable = false)
    private String lastFour;

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "payment_number")
    private String paymentNumber;

    @OneToOne
    @JoinColumn(unique = true)
    private Payment payment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreditCardType getCcType() {
        return ccType;
    }

    public CreditCardPayment ccType(CreditCardType ccType) {
        this.ccType = ccType;
        return this;
    }

    public void setCcType(CreditCardType ccType) {
        this.ccType = ccType;
    }

    public String getLastFour() {
        return lastFour;
    }

    public CreditCardPayment lastFour(String lastFour) {
        this.lastFour = lastFour;
        return this;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    public String getCardName() {
        return cardName;
    }

    public CreditCardPayment cardName(String cardName) {
        this.cardName = cardName;
        return this;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public CreditCardPayment paymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
        return this;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public Payment getPayment() {
        return payment;
    }

    public CreditCardPayment payment(Payment payment) {
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
        CreditCardPayment creditCardPayment = (CreditCardPayment) o;
        if (creditCardPayment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditCardPayment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditCardPayment{" +
            "id=" + getId() +
            ", ccType='" + getCcType() + "'" +
            ", lastFour='" + getLastFour() + "'" +
            ", cardName='" + getCardName() + "'" +
            ", paymentNumber='" + getPaymentNumber() + "'" +
            "}";
    }
}
