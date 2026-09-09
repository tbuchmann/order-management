package de.hof.university.moproco.ordermanagement.dto;

import de.hof.university.moproco.ordermanagement.entity.OrderEntity;
import de.hof.university.moproco.ordermanagement.entity.OrderStatus;

public record OrderEntityDto(
    Long id,
    java.time.LocalDate orderDate,
    java.time.LocalDate desiredShippingDate,
    OrderStatus orderStatus,
    double orderValue
) {
    public static OrderEntityDto from(OrderEntity entity) {
        return new OrderEntityDto(
            entity.getOrderId(),
            entity.getOrderDate(),
            entity.getDesiredShippingDate(),
            entity.getOrderStatus(),
            entity.getOrderValue()
        );
    }

    public OrderEntity toEntity() {
        OrderEntity entity = new OrderEntity();
        entity.setOrderId(this.id);
        entity.setOrderDate(this.orderDate);
        entity.setDesiredShippingDate(this.desiredShippingDate);
        entity.setOrderStatus(this.orderStatus);
        entity.setOrderValue(this.orderValue);
        return entity;
    }
}
