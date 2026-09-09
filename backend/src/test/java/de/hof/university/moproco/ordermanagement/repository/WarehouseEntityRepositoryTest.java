package de.hof.university.moproco.ordermanagement.repository;

import de.hof.university.moproco.ordermanagement.entity.WarehouseEntity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.flyway.enabled=false")
class WarehouseEntityRepositoryTest {

    @Autowired
    private WarehouseEntityRepository warehouseEntityRepository;

    @Test
    void shouldSaveAndFindById() {
        WarehouseEntity warehouseEntity = new WarehouseEntity();


        WarehouseEntity saved = warehouseEntityRepository.save(warehouseEntity);
        assertThat(saved.getId()).isNotNull();

        var found = warehouseEntityRepository.findById(saved.getId());
        assertThat(found).isPresent();

    }

    @Test
    void shouldFindAll() {
        WarehouseEntity warehouseEntity = new WarehouseEntity();


        warehouseEntityRepository.save(warehouseEntity);

        var all = warehouseEntityRepository.findAll();
        assertThat(all).isNotEmpty();
    }

    @Test
    void shouldDelete() {
        WarehouseEntity warehouseEntity = new WarehouseEntity();


        WarehouseEntity saved = warehouseEntityRepository.save(warehouseEntity);
        warehouseEntityRepository.delete(saved);

        var found = warehouseEntityRepository.findById(saved.getId());
        assertThat(found).isEmpty();
    }
}
