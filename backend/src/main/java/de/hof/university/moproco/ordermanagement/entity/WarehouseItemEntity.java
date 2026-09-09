package de.hof.university.moproco.ordermanagement.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "warehouse_item_entity")
public class WarehouseItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long currentStock;
    @Column(nullable = false)
    private String unit;
    @Column(nullable = false)
    private Long minimumStockLevel;
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public WarehouseItemEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCurrentStock() { return currentStock; }
    public void setCurrentStock(Long currentStock) { this.currentStock = currentStock; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public Long getMinimumStockLevel() { return minimumStockLevel; }
    public void setMinimumStockLevel(Long minimumStockLevel) { this.minimumStockLevel = minimumStockLevel; }
    public ProductEntity getProduct() { return product; }
    public void setProduct(ProductEntity product) { this.product = product; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseItemEntity that = (WarehouseItemEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
