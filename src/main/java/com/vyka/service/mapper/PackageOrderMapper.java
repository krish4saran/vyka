package com.vyka.service.mapper;

import com.vyka.domain.*;
import com.vyka.service.dto.PackageOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PackageOrder and its DTO PackageOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PackageOrderMapper extends EntityMapper<PackageOrderDTO, PackageOrder> {

    

    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "orderActivities", ignore = true)
    PackageOrder toEntity(PackageOrderDTO packageOrderDTO);

    default PackageOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        PackageOrder packageOrder = new PackageOrder();
        packageOrder.setId(id);
        return packageOrder;
    }
}
