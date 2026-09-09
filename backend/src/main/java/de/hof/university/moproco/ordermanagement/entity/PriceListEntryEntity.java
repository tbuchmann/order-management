package de.hof.university.moproco.ordermanagement.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "price_list_entry_entity")
public class PriceListEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pleId;

    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private String currency;
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public PriceListEntryEntity() {}

    public Long getPleId() { return pleId; }
    public void setPleId(Long pleId) { this.pleId = pleId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public ProductEntity getProduct() { return product; }
    public void setProduct(ProductEntity product) { this.product = product; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceListEntryEntity that = (PriceListEntryEntity) o;
        return Objects.equals(pleId, that.pleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pleId);
    }
}
