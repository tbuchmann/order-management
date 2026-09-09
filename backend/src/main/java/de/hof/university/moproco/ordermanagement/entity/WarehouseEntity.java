package de.hof.university.moproco.ordermanagement.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "warehouse_entity")
public class WarehouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany()
    @JoinColumn(name = "products_in_stock_id")
    private List<WarehouseItemEntity> productsInStockList;

    public WarehouseEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public List<WarehouseItemEntity> getProductsInStockList() { return productsInStockList; }
    public void setProductsInStockList(List<WarehouseItemEntity> productsInStockList) { this.productsInStockList = productsInStockList; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseEntity that = (WarehouseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
