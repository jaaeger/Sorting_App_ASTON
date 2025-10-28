package aston.app.input.validator;

import aston.app.input.model.NameField;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void whenCheckRecipientNameWithNonEmpty_thenReturnsTrue() {
        assertTrue(Validator.check(NameField.RECIPIENT_NAME, "John Doe"));
    }

    @Test
    void whenCheckRecipientNameEmpty_thenReturnsFalse() {
        assertFalse(Validator.check(NameField.RECIPIENT_NAME, "   "));
    }

    @Test
    void whenCheckWeightValidNumber_thenReturnsTrue() {
        assertTrue(Validator.check(NameField.WEIGHT, "10.5"));
    }

    @Test
    void whenCheckWeightNegative_thenReturnsFalse() {
        assertFalse(Validator.check(NameField.WEIGHT, "-2.5"));
    }

    @Test
    void whenCheckWeightNotNumber_thenReturnsFalse() {
        assertFalse(Validator.check(NameField.WEIGHT, "abc"));
    }

    @Test
    void whenCheckTrackingNumberValid_thenReturnsTrue() {
        assertTrue(Validator.check(NameField.TRACKING_NUMBER, "123"));
    }

    @Test
    void whenCheckTrackingNumberInvalidString_thenReturnsFalse() {
        assertFalse(Validator.check(NameField.TRACKING_NUMBER, "x5"));
    }

    @Test
    void whenCheckMaxDimensionZero_thenReturnsFalse() {
        assertFalse(Validator.check(NameField.MAX_DIMENSION, "0"));
    }

    @Test
    void whenCheckDeliveryDeadlineFuture_thenReturnsTrue() {
        assertTrue(Validator.check(NameField.DELIVERY_DEADLINE, LocalDate.now().plusDays(1).toString()));
    }

    @Test
    void whenCheckDeliveryDeadlineIncorrectFormat_thenReturnsFalse() {
        assertFalse(Validator.check(NameField.DELIVERY_DEADLINE, "2025/10/10"));
    }

    @Test
    void whenCheckDeliveryDeadlineToday_thenReturnsFalse() {
        assertFalse(Validator.check(NameField.DELIVERY_DEADLINE, LocalDate.now().toString()));
    }

    @Test
    void whenCheckDestinationCountryUpperCase_thenReturnsTrue() {
        assertTrue(Validator.check(NameField.DESTINATION_COUNTRY, "USA"));
    }

    @Test
    void whenCheckDestinationCountryUnknown_thenReturnsFalse() {
        assertFalse(Validator.check(NameField.DESTINATION_COUNTRY, "Mars"));
    }
}
