package com.vyka.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PaypalPayment entity.
 */
public class PaypalPaymentDTO implements Serializable {

    private Long id;

    @NotNull
    private String paypalPayerId;

    @NotNull
    private String paypalPayerEmailId;

    private String payerFirstName;

    private String payerLastName;

    private String paymentNumber;

    private Long paymentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaypalPayerId() {
        return paypalPayerId;
    }

    public void setPaypalPayerId(String paypalPayerId) {
        this.paypalPayerId = paypalPayerId;
    }

    public String getPaypalPayerEmailId() {
        return paypalPayerEmailId;
    }

    public void setPaypalPayerEmailId(String paypalPayerEmailId) {
        this.paypalPayerEmailId = paypalPayerEmailId;
    }

    public String getPayerFirstName() {
        return payerFirstName;
    }

    public void setPayerFirstName(String payerFirstName) {
        this.payerFirstName = payerFirstName;
    }

    public String getPayerLastName() {
        return payerLastName;
    }

    public void setPayerLastName(String payerLastName) {
        this.payerLastName = payerLastName;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
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

        PaypalPaymentDTO paypalPaymentDTO = (PaypalPaymentDTO) o;
        if(paypalPaymentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paypalPaymentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaypalPaymentDTO{" +
            "id=" + getId() +
            ", paypalPayerId='" + getPaypalPayerId() + "'" +
            ", paypalPayerEmailId='" + getPaypalPayerEmailId() + "'" +
            ", payerFirstName='" + getPayerFirstName() + "'" +
            ", payerLastName='" + getPayerLastName() + "'" +
            ", paymentNumber='" + getPaymentNumber() + "'" +
            "}";
    }
}
