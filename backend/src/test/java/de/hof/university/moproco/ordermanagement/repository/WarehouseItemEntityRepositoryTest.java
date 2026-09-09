package de.hof.university.moproco.ordermanagement.repository;

import de.hof.university.moproco.ordermanagement.entity.WarehouseItemEntity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.flyway.enabled=false")
class WarehouseItemEntityRepositoryTest {

    @Autowired
    private WarehouseItemEntityRepository warehouseItemEntityRepository;

    @Test
    void shouldSaveAndFindById() {
        WarehouseItemEntity warehouseItemEntity = new WarehouseItemEntity();
        warehouseItemEntity.setCurrentStock(42L);
        warehouseItemEntity.setUnit("test-value");
        warehouseItemEntity.setMinimumStockLevel(42L);

        WarehouseItemEntity saved = warehouseItemEntityRepository.save(warehouseItemEntity);
        assertThat(saved.getId()).isNotNull();

        var found = warehouseItemEntityRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getCurrentStock()).isEqualTo(42L);
        assertThat(found.get().getUnit()).isEqualTo("test-value");
        assertThat(found.get().getMinimumStockLevel()).isEqualTo(42L);
    }

    @Test
    void shouldFindAll() {
        WarehouseItemEntity warehouseItemEntity = new WarehouseItemEntity();
        warehouseItemEntity.setCurrentStock(42L);
        warehouseItemEntity.setUnit("test-value");
        warehouseItemEntity.setMinimumStockLevel(42L);

        warehouseItemEntityRepository.save(warehouseItemEntity);

        var all = warehouseItemEntityRepository.findAll();
        assertThat(all).isNotEmpty();
    }

    @Test
    void shouldDelete() {
        WarehouseItemEntity warehouseItemEntity = new WarehouseItemEntity();
        warehouseItemEntity.setCurrentStock(42L);
        warehouseItemEntity.setUnit("test-value");
        warehouseItemEntity.setMinimumStockLevel(42L);

        WarehouseItemEntity saved = warehouseItemEntityRepository.save(warehouseItemEntity);
        warehouseItemEntityRepository.delete(saved);

        var found = warehouseItemEntityRepository.findById(saved.getId());
        assertThat(found).isEmpty();
    }
}
