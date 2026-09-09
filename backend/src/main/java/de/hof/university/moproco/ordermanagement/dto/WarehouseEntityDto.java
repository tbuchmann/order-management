package de.hof.university.moproco.ordermanagement.dto;

import de.hof.university.moproco.ordermanagement.entity.WarehouseEntity;

public record WarehouseEntityDto(
    Long id
) {
    public static WarehouseEntityDto from(WarehouseEntity entity) {
        return new WarehouseEntityDto(
            entity.getId()
        );
    }

    public WarehouseEntity toEntity() {
        WarehouseEntity entity = new WarehouseEntity();
        entity.setId(this.id);

        return entity;
    }
}
