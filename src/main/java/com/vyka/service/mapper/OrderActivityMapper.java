package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.OrderActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderActivity and its DTO OrderActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {PackageOrderMapper.class, SettlementMapper.class})
public interface OrderActivityMapper extends EntityMapper<OrderActivityDTO, OrderActivity> {

    @Mapping(source = "packageOrder.id", target = "packageOrderId")
    @Mapping(source = "settlement.id", target = "settlementId")
    OrderActivityDTO toDto(OrderActivity orderActivity); 

    @Mapping(source = "packageOrderId", target = "packageOrder")
    @Mapping(source = "settlementId", target = "settlement")
    @Mapping(target = "scheduleActivities", ignore = true)
    OrderActivity toEntity(OrderActivityDTO orderActivityDTO);

    default OrderActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderActivity orderActivity = new OrderActivity();
        orderActivity.setId(id);
        return orderActivity;
    }
}
