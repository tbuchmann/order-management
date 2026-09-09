package de.hof.university.moproco.ordermanagement.validation;

import de.hof.university.moproco.ordermanagement.entity.PriceListEntryEntity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PriceListEntryEntityValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldPassValidationWithValidData() {
        PriceListEntryEntity entity = new PriceListEntryEntity();
        entity.setPleId(1L);
        entity.setPrice(3.14);
        entity.setCurrency("test-value");

        var violations = validator.validate(entity);
        assertThat(violations).isEmpty();
    }

}
