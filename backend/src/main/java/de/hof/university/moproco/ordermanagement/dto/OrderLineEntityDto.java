package de.hof.university.moproco.ordermanagement.dto;

import de.hof.university.moproco.ordermanagement.entity.OrderLineEntity;

public record OrderLineEntityDto(
    Long id,
    Long quantity,
    String unit,
    double unitPrice,
    String currency,
    Long productId,
    String productName
) {
    public static OrderLineEntityDto from(OrderLineEntity entity) {
        return new OrderLineEntityDto(
            entity.getLineNumber(),
            entity.getQuantity(),
            entity.getUnit(),
            entity.getUnitPrice(),
            entity.getCurrency(),
            entity.getProduct() != null ? entity.getProduct().getProductNumber() : null,
            entity.getProduct() != null ? entity.getProduct().getProductName() : null
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
