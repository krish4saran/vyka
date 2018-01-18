package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.CreditCardPaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CreditCardPayment and its DTO CreditCardPaymentDTO.
 */
@Mapper(componentModel = "spring", uses = {PaymentMapper.class})
public interface CreditCardPaymentMapper extends EntityMapper<CreditCardPaymentDTO, CreditCardPayment> {

    @Mapping(source = "payment.id", target = "paymentId")
    CreditCardPaymentDTO toDto(CreditCardPayment creditCardPayment); 

    @Mapping(source = "paymentId", target = "payment")
    CreditCardPayment toEntity(CreditCardPaymentDTO creditCardPaymentDTO);

    default CreditCardPayment fromId(Long id) {
        if (id == null) {
            return null;
        }
        CreditCardPayment creditCardPayment = new CreditCardPayment();
        creditCardPayment.setId(id);
        return creditCardPayment;
    }
}
