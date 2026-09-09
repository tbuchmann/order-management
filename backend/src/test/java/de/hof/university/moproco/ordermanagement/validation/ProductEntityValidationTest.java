package de.hof.university.moproco.ordermanagement.validation;

import de.hof.university.moproco.ordermanagement.entity.ProductEntity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ProductEntityValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldPassValidationWithValidData() {
        ProductEntity entity = new ProductEntity();
        entity.setProductNumber(1L);
        entity.setProductName("test-value");
        entity.setProductCategory("test-value");

        var violations = validator.validate(entity);
        assertThat(violations).isEmpty();
    }

}
