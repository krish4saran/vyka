package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.SettlementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Settlement and its DTO SettlementDTO.
 */
@Mapper(componentModel = "spring", uses = {PaymentMapper.class})
public interface SettlementMapper extends EntityMapper<SettlementDTO, Settlement> {

    @Mapping(source = "payment.id", target = "paymentId")
    SettlementDTO toDto(Settlement settlement); 

    @Mapping(source = "paymentId", target = "payment")
    Settlement toEntity(SettlementDTO settlementDTO);

    default Settlement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Settlement settlement = new Settlement();
        settlement.setId(id);
        return settlement;
    }
}
