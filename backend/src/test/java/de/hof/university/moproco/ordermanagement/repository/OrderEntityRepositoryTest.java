package de.hof.university.moproco.ordermanagement.repository;

import de.hof.university.moproco.ordermanagement.entity.OrderEntity;
import de.hof.university.moproco.ordermanagement.entity.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.flyway.enabled=false")
class OrderEntityRepositoryTest {

    @Autowired
    private OrderEntityRepository orderEntityRepository;

    @Test
    void shouldSaveAndFindById() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderDate(java.time.LocalDate.of(2024, 1, 1));
        orderEntity.setDesiredShippingDate(java.time.LocalDate.of(2024, 1, 1));
        orderEntity.setOrderStatus(OrderStatus.values()[0]);
        orderEntity.setOrderValue(3.14);

        OrderEntity saved = orderEntityRepository.save(orderEntity);
        assertThat(saved.getOrderId()).isNotNull();

        var found = orderEntityRepository.findById(saved.getOrderId());
        assertThat(found).isPresent();
        assertThat(found.get().getOrderDate()).isEqualTo(java.time.LocalDate.of(2024, 1, 1));
        assertThat(found.get().getDesiredShippingDate()).isEqualTo(java.time.LocalDate.of(2024, 1, 1));
        assertThat(found.get().getOrderStatus()).isEqualTo(OrderStatus.values()[0]);
        assertThat(found.get().getOrderValue()).isEqualTo(3.14);
    }

    @Test
    void shouldFindAll() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderDate(java.time.LocalDate.of(2024, 1, 1));
        orderEntity.setDesiredShippingDate(java.time.LocalDate.of(2024, 1, 1));
        orderEntity.setOrderStatus(OrderStatus.values()[0]);
        orderEntity.setOrderValue(3.14);

        orderEntityRepository.save(orderEntity);

        var all = orderEntityRepository.findAll();
        assertThat(all).isNotEmpty();
    }

    @Test
    void shouldDelete() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderDate(java.time.LocalDate.of(2024, 1, 1));
        orderEntity.setDesiredShippingDate(java.time.LocalDate.of(2024, 1, 1));
        orderEntity.setOrderStatus(OrderStatus.values()[0]);
        orderEntity.setOrderValue(3.14);

        OrderEntity saved = orderEntityRepository.save(orderEntity);
        orderEntityRepository.delete(saved);

        var found = orderEntityRepository.findById(saved.getOrderId());
        assertThat(found).isEmpty();
    }
}
