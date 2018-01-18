package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.PaypalPaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PaypalPayment and its DTO PaypalPaymentDTO.
 */
@Mapper(componentModel = "spring", uses = {PaymentMapper.class})
public interface PaypalPaymentMapper extends EntityMapper<PaypalPaymentDTO, PaypalPayment> {

    @Mapping(source = "payment.id", target = "paymentId")
    PaypalPaymentDTO toDto(PaypalPayment paypalPayment); 

    @Mapping(source = "paymentId", target = "payment")
    PaypalPayment toEntity(PaypalPaymentDTO paypalPaymentDTO);

    default PaypalPayment fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaypalPayment paypalPayment = new PaypalPayment();
        paypalPayment.setId(id);
        return paypalPayment;
    }
}
