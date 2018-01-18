package com.vyka.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PaypalPayment.
 */
@Entity
@Table(name = "paypal_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "paypalpayment")
public class PaypalPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "paypal_payer_id", nullable = false)
    private String paypalPayerId;

    @NotNull
    @Column(name = "paypal_payer_email_id", nullable = false)
    private String paypalPayerEmailId;

    @Column(name = "payer_first_name")
    private String payerFirstName;

    @Column(name = "payer_last_name")
    private String payerLastName;

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

    public String getPaypalPayerId() {
        return paypalPayerId;
    }

    public PaypalPayment paypalPayerId(String paypalPayerId) {
        this.paypalPayerId = paypalPayerId;
        return this;
    }

    public void setPaypalPayerId(String paypalPayerId) {
        this.paypalPayerId = paypalPayerId;
    }

    public String getPaypalPayerEmailId() {
        return paypalPayerEmailId;
    }

    public PaypalPayment paypalPayerEmailId(String paypalPayerEmailId) {
        this.paypalPayerEmailId = paypalPayerEmailId;
        return this;
    }

    public void setPaypalPayerEmailId(String paypalPayerEmailId) {
        this.paypalPayerEmailId = paypalPayerEmailId;
    }

    public String getPayerFirstName() {
        return payerFirstName;
    }

    public PaypalPayment payerFirstName(String payerFirstName) {
        this.payerFirstName = payerFirstName;
        return this;
    }

    public void setPayerFirstName(String payerFirstName) {
        this.payerFirstName = payerFirstName;
    }

    public String getPayerLastName() {
        return payerLastName;
    }

    public PaypalPayment payerLastName(String payerLastName) {
        this.payerLastName = payerLastName;
        return this;
    }

    public void setPayerLastName(String payerLastName) {
        this.payerLastName = payerLastName;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public PaypalPayment paymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
        return this;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public Payment getPayment() {
        return payment;
    }

    public PaypalPayment payment(Payment payment) {
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
        PaypalPayment paypalPayment = (PaypalPayment) o;
        if (paypalPayment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paypalPayment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaypalPayment{" +
            "id=" + getId() +
            ", paypalPayerId='" + getPaypalPayerId() + "'" +
            ", paypalPayerEmailId='" + getPaypalPayerEmailId() + "'" +
            ", payerFirstName='" + getPayerFirstName() + "'" +
            ", payerLastName='" + getPayerLastName() + "'" +
            ", paymentNumber='" + getPaymentNumber() + "'" +
            "}";
    }
}
