package com.vyka.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.vyka.domain.enumeration.CreditCardType;

/**
 * A DTO for the CreditCardPayment entity.
 */
public class CreditCardPaymentDTO implements Serializable {

    private Long id;

    @NotNull
    private CreditCardType ccType;

    @NotNull
    private String lastFour;

    private String cardName;

    private String paymentNumber;

    private Long paymentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreditCardType getCcType() {
        return ccType;
    }

    public void setCcType(CreditCardType ccType) {
        this.ccType = ccType;
    }

    public String getLastFour() {
        return lastFour;
    }

    public void setLastFour(String lastFour) {
        this.lastFour = lastFour;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
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

        CreditCardPaymentDTO creditCardPaymentDTO = (CreditCardPaymentDTO) o;
        if(creditCardPaymentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), creditCardPaymentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CreditCardPaymentDTO{" +
            "id=" + getId() +
            ", ccType='" + getCcType() + "'" +
            ", lastFour='" + getLastFour() + "'" +
            ", cardName='" + getCardName() + "'" +
            ", paymentNumber='" + getPaymentNumber() + "'" +
            "}";
    }
}
