package de.hof.university.moproco.ordermanagement.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_line_entity")
public class OrderLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lineNumber;

    @Column(nullable = false)
    private Long quantity;
    @Column(nullable = false)
    private String unit;
    @Column(nullable = false)
    private double unitPrice;
    @Column(nullable = false)
    private String currency;
    @ManyToOne()
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public OrderLineEntity() {}

    public Long getLineNumber() { return lineNumber; }
    public void setLineNumber(Long lineNumber) { this.lineNumber = lineNumber; }

    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public OrderEntity getOrder() { return order; }
    public void setOrder(OrderEntity order) { this.order = order; }
    public ProductEntity getProduct() { return product; }
    public void setProduct(ProductEntity product) { this.product = product; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineEntity that = (OrderLineEntity) o;
        return Objects.equals(lineNumber, that.lineNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber);
    }
}
