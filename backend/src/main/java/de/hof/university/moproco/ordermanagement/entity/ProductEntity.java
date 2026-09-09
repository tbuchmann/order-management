package de.hof.university.moproco.ordermanagement.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_entity")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productNumber;

    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private String productCategory;

    public ProductEntity() {}

    public Long getProductNumber() { return productNumber; }
    public void setProductNumber(Long productNumber) { this.productNumber = productNumber; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductCategory() { return productCategory; }
    public void setProductCategory(String productCategory) { this.productCategory = productCategory; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(productNumber, that.productNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productNumber);
    }
}
