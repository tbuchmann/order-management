package de.hof.university.moproco.ordermanagement.dto;

import de.hof.university.moproco.ordermanagement.entity.CustomerEntity;

public record CustomerEntityDto(
    Long id,
    String companyName,
    String phoneNumber,
    String companyEmail,
    Long dunsNumber
) {
    public static CustomerEntityDto from(CustomerEntity entity) {
        return new CustomerEntityDto(
            entity.getCustomerId(),
            entity.getCompanyName(),
            entity.getPhoneNumber(),
            entity.getCompanyEmail(),
            entity.getDunsNumber()
        );
    }

    public CustomerEntity toEntity() {
        CustomerEntity entity = new CustomerEntity();
        entity.setCustomerId(this.id);
        entity.setCompanyName(this.companyName);
        entity.setPhoneNumber(this.phoneNumber);
        entity.setCompanyEmail(this.companyEmail);
        entity.setDunsNumber(this.dunsNumber);
        return entity;
    }
}
