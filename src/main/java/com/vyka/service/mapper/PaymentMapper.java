package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.PaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Payment and its DTO PaymentDTO.
 */
@Mapper(componentModel = "spring", uses = {PackageOrderMapper.class})
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {

    @Mapping(source = "packageOrder.id", target = "packageOrderId")
    PaymentDTO toDto(Payment payment); 

    @Mapping(source = "packageOrderId", target = "packageOrder")
    @Mapping(target = "settlements", ignore = true)
    Payment toEntity(PaymentDTO paymentDTO);

    default Payment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setId(id);
        return payment;
    }
}
