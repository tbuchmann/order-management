package de.hof.university.moproco.ordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import de.hof.university.moproco.ordermanagement.entity.OrderLineEntity;

public interface OrderLineEntityRepository extends JpaRepository<OrderLineEntity, Long>, JpaSpecificationExecutor<OrderLineEntity> {

}
