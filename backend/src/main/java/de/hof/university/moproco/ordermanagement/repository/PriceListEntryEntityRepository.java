package de.hof.university.moproco.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import de.hof.university.moproco.ordermanagement.entity.PriceListEntryEntity;
import java.util.Optional;

public interface PriceListEntryEntityRepository extends JpaRepository<PriceListEntryEntity, Long>, JpaSpecificationExecutor<PriceListEntryEntity> {

    Optional<PriceListEntryEntity> findFirstByProduct_ProductNumber(Long productNumber);
}
