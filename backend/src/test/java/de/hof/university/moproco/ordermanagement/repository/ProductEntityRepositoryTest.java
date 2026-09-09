package de.hof.university.moproco.ordermanagement.repository;

import de.hof.university.moproco.ordermanagement.entity.ProductEntity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.flyway.enabled=false")
class ProductEntityRepositoryTest {

    @Autowired
    private ProductEntityRepository productEntityRepository;

    @Test
    void shouldSaveAndFindById() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName("test-value");
        productEntity.setProductCategory("test-value");

        ProductEntity saved = productEntityRepository.save(productEntity);
        assertThat(saved.getProductNumber()).isNotNull();

        var found = productEntityRepository.findById(saved.getProductNumber());
        assertThat(found).isPresent();
        assertThat(found.get().getProductName()).isEqualTo("test-value");
        assertThat(found.get().getProductCategory()).isEqualTo("test-value");
    }

    @Test
    void shouldFindAll() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName("test-value");
        productEntity.setProductCategory("test-value");

        productEntityRepository.save(productEntity);

        var all = productEntityRepository.findAll();
        assertThat(all).isNotEmpty();
    }

    @Test
    void shouldDelete() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName("test-value");
        productEntity.setProductCategory("test-value");

        ProductEntity saved = productEntityRepository.save(productEntity);
        productEntityRepository.delete(saved);

        var found = productEntityRepository.findById(saved.getProductNumber());
        assertThat(found).isEmpty();
    }
}
