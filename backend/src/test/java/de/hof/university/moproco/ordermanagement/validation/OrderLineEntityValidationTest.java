package de.hof.university.moproco.ordermanagement.validation;

import de.hof.university.moproco.ordermanagement.entity.OrderLineEntity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OrderLineEntityValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldPassValidationWithValidData() {
        OrderLineEntity entity = new OrderLineEntity();
        entity.setLineNumber(1L);
        entity.setQuantity(42L);
        entity.setUnit("test-value");
        entity.setUnitPrice(3.14);
        entity.setCurrency("test-value");

        var violations = validator.validate(entity);
        assertThat(violations).isEmpty();
    }

}
