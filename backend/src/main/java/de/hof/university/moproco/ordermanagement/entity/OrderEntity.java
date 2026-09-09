package de.hof.university.moproco.ordermanagement.entity;

import jakarta.persistence.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order_entity")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private java.time.LocalDate orderDate;
    @Column(nullable = false)
    private java.time.LocalDate desiredShippingDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;
    @Transient
    // derived: sum(lineItems.quantity * lineItems.unitPrice)
    private double orderValue;
    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
    @ManyToOne()
    @JoinColumn(name = "shippingAddress_id")
    private DeliveryAddressEntity shippingAddress;
    @ManyToOne()
    @JoinColumn(name = "billingAddress_id")
    private DeliveryAddressEntity billingAddress;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLineEntity> lineItemsList;

    public OrderEntity() {}

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public java.time.LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(java.time.LocalDate orderDate) { this.orderDate = orderDate; }
    public java.time.LocalDate getDesiredShippingDate() { return desiredShippingDate; }
    public void setDesiredShippingDate(java.time.LocalDate desiredShippingDate) { this.desiredShippingDate = desiredShippingDate; }
    public OrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }
    public double getOrderValue() { return orderValue; }
    public void setOrderValue(double orderValue) { this.orderValue = orderValue; }
    public CustomerEntity getCustomer() { return customer; }
    public void setCustomer(CustomerEntity customer) { this.customer = customer; }
    public DeliveryAddressEntity getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(DeliveryAddressEntity shippingAddress) { this.shippingAddress = shippingAddress; }
    public DeliveryAddressEntity getBillingAddress() { return billingAddress; }
    public void setBillingAddress(DeliveryAddressEntity billingAddress) { this.billingAddress = billingAddress; }
    public List<OrderLineEntity> getLineItemsList() { return lineItemsList; }
    public void setLineItemsList(List<OrderLineEntity> lineItemsList) { this.lineItemsList = lineItemsList; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
