package de.hof.university.moproco.ordermanagement.repository;

import de.hof.university.moproco.ordermanagement.entity.DeliveryAddressEntity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.flyway.enabled=false")
class DeliveryAddressEntityRepositoryTest {

    @Autowired
    private DeliveryAddressEntityRepository deliveryAddressEntityRepository;

    @Test
    void shouldSaveAndFindById() {
        DeliveryAddressEntity deliveryAddressEntity = new DeliveryAddressEntity();
        deliveryAddressEntity.setStreet("test-value");
        deliveryAddressEntity.setPostcode("test-value");
        deliveryAddressEntity.setCity("test-value");
        deliveryAddressEntity.setCountry("test-value");

        DeliveryAddressEntity saved = deliveryAddressEntityRepository.save(deliveryAddressEntity);
        assertThat(saved.getId()).isNotNull();

        var found = deliveryAddressEntityRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getStreet()).isEqualTo("test-value");
        assertThat(found.get().getPostcode()).isEqualTo("test-value");
        assertThat(found.get().getCity()).isEqualTo("test-value");
        assertThat(found.get().getCountry()).isEqualTo("test-value");
    }

    @Test
    void shouldFindAll() {
        DeliveryAddressEntity deliveryAddressEntity = new DeliveryAddressEntity();
        deliveryAddressEntity.setStreet("test-value");
        deliveryAddressEntity.setPostcode("test-value");
        deliveryAddressEntity.setCity("test-value");
        deliveryAddressEntity.setCountry("test-value");

        deliveryAddressEntityRepository.save(deliveryAddressEntity);

        var all = deliveryAddressEntityRepository.findAll();
        assertThat(all).isNotEmpty();
    }

    @Test
    void shouldDelete() {
        DeliveryAddressEntity deliveryAddressEntity = new DeliveryAddressEntity();
        deliveryAddressEntity.setStreet("test-value");
        deliveryAddressEntity.setPostcode("test-value");
        deliveryAddressEntity.setCity("test-value");
        deliveryAddressEntity.setCountry("test-value");

        DeliveryAddressEntity saved = deliveryAddressEntityRepository.save(deliveryAddressEntity);
        deliveryAddressEntityRepository.delete(saved);

        var found = deliveryAddressEntityRepository.findById(saved.getId());
        assertThat(found).isEmpty();
    }
}
