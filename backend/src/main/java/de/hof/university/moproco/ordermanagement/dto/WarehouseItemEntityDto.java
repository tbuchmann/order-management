package de.hof.university.moproco.ordermanagement.dto;

import de.hof.university.moproco.ordermanagement.entity.WarehouseItemEntity;

public record WarehouseItemEntityDto(
    Long id,
    Long currentStock,
    String unit,
    Long minimumStockLevel
) {
    public static WarehouseItemEntityDto from(WarehouseItemEntity entity) {
        return new WarehouseItemEntityDto(
            entity.getId(),
            entity.getCurrentStock(),
            entity.getUnit(),
            entity.getMinimumStockLevel()
        );
    }

    public WarehouseItemEntity toEntity() {
        WarehouseItemEntity entity = new WarehouseItemEntity();
        entity.setId(this.id);
        entity.setCurrentStock(this.currentStock);
        entity.setUnit(this.unit);
        entity.setMinimumStockLevel(this.minimumStockLevel);
        return entity;
    }
}
