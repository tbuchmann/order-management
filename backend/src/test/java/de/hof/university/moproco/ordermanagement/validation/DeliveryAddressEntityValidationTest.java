package de.hof.university.moproco.ordermanagement.validation;

import de.hof.university.moproco.ordermanagement.entity.DeliveryAddressEntity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class DeliveryAddressEntityValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldPassValidationWithValidData() {
        DeliveryAddressEntity entity = new DeliveryAddressEntity();
        entity.setId(1L);
        entity.setStreet("test-value");
        entity.setPostcode("test-value");
        entity.setCity("test-value");
        entity.setCountry("test-value");

        var violations = validator.validate(entity);
        assertThat(violations).isEmpty();
    }

}
