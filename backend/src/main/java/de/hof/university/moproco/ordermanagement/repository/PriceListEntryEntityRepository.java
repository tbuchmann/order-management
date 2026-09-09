package de.hof.university.moproco.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import de.hof.university.moproco.ordermanagement.entity.PriceListEntryEntity;

public interface PriceListEntryEntityRepository extends JpaRepository<PriceListEntryEntity, Long>, JpaSpecificationExecutor<PriceListEntryEntity> {

}
