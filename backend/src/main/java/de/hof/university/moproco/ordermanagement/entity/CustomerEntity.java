package de.hof.university.moproco.ordermanagement.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "customer_entity")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false)
    private String companyName;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String companyEmail;
    @Column(nullable = false)
    private Long dunsNumber;
    @ManyToOne()
    @JoinColumn(name = "companyAddress_id")
    private DeliveryAddressEntity companyAddress;

    public CustomerEntity() {}

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getCompanyEmail() { return companyEmail; }
    public void setCompanyEmail(String companyEmail) { this.companyEmail = companyEmail; }
    public Long getDunsNumber() { return dunsNumber; }
    public void setDunsNumber(Long dunsNumber) { this.dunsNumber = dunsNumber; }
    public DeliveryAddressEntity getCompanyAddress() { return companyAddress; }
    public void setCompanyAddress(DeliveryAddressEntity companyAddress) { this.companyAddress = companyAddress; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}
