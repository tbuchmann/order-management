package de.hof.university.moproco.ordermanagement.dto;

import de.hof.university.moproco.ordermanagement.entity.ProductEntity;

public record ProductEntityDto(
    Long id,
    String productName,
    String productCategory
) {
    public static ProductEntityDto from(ProductEntity entity) {
        return new ProductEntityDto(
            entity.getProductNumber(),
            entity.getProductName(),
            entity.getProductCategory()
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
