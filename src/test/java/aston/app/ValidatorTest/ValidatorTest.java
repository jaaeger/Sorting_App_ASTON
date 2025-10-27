package aston.app.ValidatorTest;

import aston.app.input.NameField;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void whenCheckRecipientNameWithValidName_thenReturnsTrue() {
        boolean result = Validator.check(NameField.RECIPIENT_NAME, "John Doe");
        assertTrue(result);
    }

    @Test
    void whenCheckWeightWithValidValue_thenReturnsTrue() {
        boolean result = Validator.check(NameField.WEIGHT, "10.5");
        assertTrue(result);
    }

    @Test
    void whenCheckWeightWithNegativeValue_thenReturnsFalse() {
        boolean result = Validator.check(NameField.WEIGHT, "-5.0");
        assertFalse(result);
    }

    @Test
    void whenCheckTrackingNumberWithValidValue_thenReturnsTrue() {
        boolean result = Validator.check(NameField.TRACKING_NUMBER, "12345");
        assertTrue(result);
    }

    @Test
    void whenCheckTrackingNumberWithNegativeValue_thenReturnsFalse() {
        boolean result = Validator.check(NameField.TRACKING_NUMBER, "-123");
        assertFalse(result);
    }

    @Test
    void whenCheckMaxDimensionWithValidValue_thenReturnsTrue() {
        boolean result = Validator.check(NameField.MAX_DIMENSION, "25");
        assertTrue(result);
    }

    @Test
    void whenCheckMaxDimensionWithNegativeValue_thenReturnsFalse() {
        boolean result = Validator.check(NameField.MAX_DIMENSION, "-10");
        assertFalse(result);
    }

    @Test
    void whenCheckDeliveryDeadlineWithFutureDate_thenReturnsTrue() {
        String futureDate = LocalDate.now().plusDays(1).toString();
        boolean result = Validator.check(NameField.DELIVERY_DEADLINE, futureDate);
        assertTrue(result);
    }

    @Test
    void whenCheckDeliveryDeadlineWithPastDate_thenReturnsFalse() {
        String pastDate = LocalDate.now().minusDays(1).toString();
        boolean result = Validator.check(NameField.DELIVERY_DEADLINE, pastDate);
        assertFalse(result);
    }

    @Test
    void whenCheckDestinationCountryWithValidCountry_thenReturnsTrue() {
        boolean result = Validator.check(NameField.DESTINATION_COUNTRY, "USA");
        assertTrue(result);
    }

    @Test
    void whenCheckDestinationCountryWithInvalidCountry_thenReturnsFalse() {
        boolean result = Validator.check(NameField.DESTINATION_COUNTRY, "Mars");
        assertFalse(result);
    }
}