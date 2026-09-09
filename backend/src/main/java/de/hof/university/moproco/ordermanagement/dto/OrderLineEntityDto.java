package de.hof.university.moproco.ordermanagement.dto;

import de.hof.university.moproco.ordermanagement.entity.OrderLineEntity;

public record OrderLineEntityDto(
    Long id,
    Long quantity,
    String unit,
    double unitPrice,
    String currency
) {
    public static OrderLineEntityDto from(OrderLineEntity entity) {
        return new OrderLineEntityDto(
            entity.getLineNumber(),
            entity.getQuantity(),
            entity.getUnit(),
            entity.getUnitPrice(),
            entity.getCurrency()
        );
    }

    public OrderLineEntity toEntity() {
        OrderLineEntity entity = new OrderLineEntity();
        entity.setLineNumber(this.id);
        entity.setQuantity(this.quantity);
        entity.setUnit(this.unit);
        entity.setUnitPrice(this.unitPrice);
        entity.setCurrency(this.currency);
        return entity;
    }
}
