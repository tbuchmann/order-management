package de.hof.university.moproco.ordermanagement.repository;

import de.hof.university.moproco.ordermanagement.entity.OrderLineEntity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.flyway.enabled=false")
class OrderLineEntityRepositoryTest {

    @Autowired
    private OrderLineEntityRepository orderLineEntityRepository;

    @Test
    void shouldSaveAndFindById() {
        OrderLineEntity orderLineEntity = new OrderLineEntity();
        orderLineEntity.setQuantity(42L);
        orderLineEntity.setUnit("test-value");
        orderLineEntity.setUnitPrice(3.14);
        orderLineEntity.setCurrency("test-value");

        OrderLineEntity saved = orderLineEntityRepository.save(orderLineEntity);
        assertThat(saved.getLineNumber()).isNotNull();

        var found = orderLineEntityRepository.findById(saved.getLineNumber());
        assertThat(found).isPresent();
        assertThat(found.get().getQuantity()).isEqualTo(42L);
        assertThat(found.get().getUnit()).isEqualTo("test-value");
        assertThat(found.get().getUnitPrice()).isEqualTo(3.14);
        assertThat(found.get().getCurrency()).isEqualTo("test-value");
    }

    @Test
    void shouldFindAll() {
        OrderLineEntity orderLineEntity = new OrderLineEntity();
        orderLineEntity.setQuantity(42L);
        orderLineEntity.setUnit("test-value");
        orderLineEntity.setUnitPrice(3.14);
        orderLineEntity.setCurrency("test-value");

        orderLineEntityRepository.save(orderLineEntity);

        var all = orderLineEntityRepository.findAll();
        assertThat(all).isNotEmpty();
    }

    @Test
    void shouldDelete() {
        OrderLineEntity orderLineEntity = new OrderLineEntity();
        orderLineEntity.setQuantity(42L);
        orderLineEntity.setUnit("test-value");
        orderLineEntity.setUnitPrice(3.14);
        orderLineEntity.setCurrency("test-value");

        OrderLineEntity saved = orderLineEntityRepository.save(orderLineEntity);
        orderLineEntityRepository.delete(saved);

        var found = orderLineEntityRepository.findById(saved.getLineNumber());
        assertThat(found).isEmpty();
    }
}
