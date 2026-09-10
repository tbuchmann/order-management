package de.hof.university.moproco.ordermanagement.dto;

import de.hof.university.moproco.ordermanagement.entity.ProductEntity;

public record ProductEntityDto(
    Long id,
    String productName,
    String productCategory,
    Double price,
    String currency
) {
    public static ProductEntityDto from(ProductEntity entity) {
        return new ProductEntityDto(
            entity.getProductNumber(),
            entity.getProductName(),
            entity.getProductCategory(),
            null,
            null
        );
    }

    public static ProductEntityDto from(ProductEntity entity, Double price, String currency) {
        return new ProductEntityDto(
            entity.getProductNumber(),
            entity.getProductName(),
            entity.getProductCategory(),
            price,
            currency
        );
    }

    public ProductEntity toEntity() {
        ProductEntity entity = new ProductEntity();
        entity.setProductNumber(this.id);
        entity.setProductName(this.productName);
        entity.setProductCategory(this.productCategory);
        return entity;
    }
}
