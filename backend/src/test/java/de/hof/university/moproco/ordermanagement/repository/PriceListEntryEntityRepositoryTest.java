package de.hof.university.moproco.ordermanagement.repository;

import de.hof.university.moproco.ordermanagement.entity.PriceListEntryEntity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.flyway.enabled=false")
class PriceListEntryEntityRepositoryTest {

    @Autowired
    private PriceListEntryEntityRepository priceListEntryEntityRepository;

    @Test
    void shouldSaveAndFindById() {
        PriceListEntryEntity priceListEntryEntity = new PriceListEntryEntity();
        priceListEntryEntity.setPrice(3.14);
        priceListEntryEntity.setCurrency("test-value");

        PriceListEntryEntity saved = priceListEntryEntityRepository.save(priceListEntryEntity);
        assertThat(saved.getPleId()).isNotNull();

        var found = priceListEntryEntityRepository.findById(saved.getPleId());
        assertThat(found).isPresent();
        assertThat(found.get().getPrice()).isEqualTo(3.14);
        assertThat(found.get().getCurrency()).isEqualTo("test-value");
    }

    @Test
    void shouldFindAll() {
        PriceListEntryEntity priceListEntryEntity = new PriceListEntryEntity();
        priceListEntryEntity.setPrice(3.14);
        priceListEntryEntity.setCurrency("test-value");

        priceListEntryEntityRepository.save(priceListEntryEntity);

        var all = priceListEntryEntityRepository.findAll();
        assertThat(all).isNotEmpty();
    }

    @Test
    void shouldDelete() {
        PriceListEntryEntity priceListEntryEntity = new PriceListEntryEntity();
        priceListEntryEntity.setPrice(3.14);
        priceListEntryEntity.setCurrency("test-value");

        PriceListEntryEntity saved = priceListEntryEntityRepository.save(priceListEntryEntity);
        priceListEntryEntityRepository.delete(saved);

        var found = priceListEntryEntityRepository.findById(saved.getPleId());
        assertThat(found).isEmpty();
    }
}
