package de.hof.university.moproco.ordermanagement.dto;

import de.hof.university.moproco.ordermanagement.entity.PriceListEntryEntity;

public record PriceListEntryEntityDto(
    Long id,
    double price,
    String currency
) {
    public static PriceListEntryEntityDto from(PriceListEntryEntity entity) {
        return new PriceListEntryEntityDto(
            entity.getPleId(),
            entity.getPrice(),
            entity.getCurrency()
        );
    }

    public PriceListEntryEntity toEntity() {
        PriceListEntryEntity entity = new PriceListEntryEntity();
        entity.setPleId(this.id);
        entity.setPrice(this.price);
        entity.setCurrency(this.currency);
        return entity;
    }
}
