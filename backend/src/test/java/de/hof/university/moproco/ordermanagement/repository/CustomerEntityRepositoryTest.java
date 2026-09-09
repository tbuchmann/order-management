package de.hof.university.moproco.ordermanagement.repository;

import de.hof.university.moproco.ordermanagement.entity.CustomerEntity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.flyway.enabled=false")
class CustomerEntityRepositoryTest {

    @Autowired
    private CustomerEntityRepository customerEntityRepository;

    @Test
    void shouldSaveAndFindById() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCompanyName("test-value");
        customerEntity.setPhoneNumber("test-value");
        customerEntity.setCompanyEmail("test@example.com");
        customerEntity.setDunsNumber(42L);

        CustomerEntity saved = customerEntityRepository.save(customerEntity);
        assertThat(saved.getCustomerId()).isNotNull();

        var found = customerEntityRepository.findById(saved.getCustomerId());
        assertThat(found).isPresent();
        assertThat(found.get().getCompanyName()).isEqualTo("test-value");
        assertThat(found.get().getPhoneNumber()).isEqualTo("test-value");
        assertThat(found.get().getCompanyEmail()).isEqualTo("test@example.com");
        assertThat(found.get().getDunsNumber()).isEqualTo(42L);
    }

    @Test
    void shouldFindAll() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCompanyName("test-value");
        customerEntity.setPhoneNumber("test-value");
        customerEntity.setCompanyEmail("test@example.com");
        customerEntity.setDunsNumber(42L);

        customerEntityRepository.save(customerEntity);

        var all = customerEntityRepository.findAll();
        assertThat(all).isNotEmpty();
    }

    @Test
    void shouldDelete() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCompanyName("test-value");
        customerEntity.setPhoneNumber("test-value");
        customerEntity.setCompanyEmail("test@example.com");
        customerEntity.setDunsNumber(42L);

        CustomerEntity saved = customerEntityRepository.save(customerEntity);
        customerEntityRepository.delete(saved);

        var found = customerEntityRepository.findById(saved.getCustomerId());
        assertThat(found).isEmpty();
    }
}
