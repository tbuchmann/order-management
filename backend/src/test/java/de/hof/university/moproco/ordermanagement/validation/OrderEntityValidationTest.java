package de.hof.university.moproco.ordermanagement.validation;

import de.hof.university.moproco.ordermanagement.entity.OrderEntity;
import de.hof.university.moproco.ordermanagement.entity.OrderStatus;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OrderEntityValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldPassValidationWithValidData() {
        OrderEntity entity = new OrderEntity();
        entity.setOrderId(1L);
        entity.setOrderDate(java.time.LocalDate.of(2024, 1, 1));
        entity.setDesiredShippingDate(java.time.LocalDate.of(2024, 1, 1));
        entity.setOrderStatus(OrderStatus.values()[0]);
        entity.setOrderValue(3.14);

        var violations = validator.validate(entity);
        assertThat(violations).isEmpty();
    }

}
