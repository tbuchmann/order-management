package de.hof.university.moproco.ordermanagement.validation;

import de.hof.university.moproco.ordermanagement.entity.CustomerEntity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerEntityValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldPassValidationWithValidData() {
        CustomerEntity entity = new CustomerEntity();
        entity.setCustomerId(1L);
        entity.setCompanyName("test-value");
        entity.setPhoneNumber("test-value");
        entity.setCompanyEmail("test@example.com");
        entity.setDunsNumber(42L);

        var violations = validator.validate(entity);
        assertThat(violations).isEmpty();
    }

}
