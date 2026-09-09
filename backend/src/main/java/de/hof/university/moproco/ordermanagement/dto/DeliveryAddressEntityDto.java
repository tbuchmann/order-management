package de.hof.university.moproco.ordermanagement.dto;

import de.hof.university.moproco.ordermanagement.entity.DeliveryAddressEntity;

public record DeliveryAddressEntityDto(
    Long id,
    String street,
    String postcode,
    String city,
    String country
) {
    public static DeliveryAddressEntityDto from(DeliveryAddressEntity entity) {
        return new DeliveryAddressEntityDto(
            entity.getId(),
            entity.getStreet(),
            entity.getPostcode(),
            entity.getCity(),
            entity.getCountry()
        );
    }

    public DeliveryAddressEntity toEntity() {
        DeliveryAddressEntity entity = new DeliveryAddressEntity();
        entity.setId(this.id);
        entity.setStreet(this.street);
        entity.setPostcode(this.postcode);
        entity.setCity(this.city);
        entity.setCountry(this.country);
        return entity;
    }
}
