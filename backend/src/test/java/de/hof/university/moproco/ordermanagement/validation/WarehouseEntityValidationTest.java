package de.hof.university.moproco.ordermanagement.validation;

import de.hof.university.moproco.ordermanagement.entity.WarehouseEntity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class WarehouseEntityValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldPassValidationWithValidData() {
        WarehouseEntity entity = new WarehouseEntity();
        entity.setId(1L);


        var violations = validator.validate(entity);
        assertThat(violations).isEmpty();
    }

}
