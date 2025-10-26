package aston.app.ValidatorTest;

import aston.app.input.NameField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void testCheck_RecipientName_Valid() {
        boolean result = Validator.check(NameField.RECIPIENT_NAME, "John Doe");
        assertTrue(result);
    }

    @Test
    void testCheck_Weight_Valid() {
        boolean result = Validator.check(NameField.WEIGHT, "10.5");
        assertTrue(result);
    }

    @Test
    void testCheck_Weight_Invalid() {
        boolean result = Validator.check(NameField.WEIGHT, "-5.0");
        assertFalse(result);
    }

    @Test
    void testCheck_TrackingNumber_Valid() {
        boolean result = Validator.check(NameField.TRACKING_NUMBER, "12345");
        assertTrue(result);
    }

    @Test
    void testCheck_TrackingNumber_Invalid() {
        boolean result = Validator.check(NameField.TRACKING_NUMBER, "-123");
        assertFalse(result);
    }

    @Test
    void testCheck_MaxDimension_Valid() {
        boolean result = Validator.check(NameField.MAX_DIMENSION, "25");
        assertTrue(result);
    }

    @Test
    void testCheck_MaxDimension_Invalid() {
        boolean result = Validator.check(NameField.MAX_DIMENSION, "-10");
        assertFalse(result);
    }

    @Test
    void testCheck_DeliveryDeadline_Valid() {
        String futureDate = LocalDate.now().plusDays(1).toString();
        boolean result = Validator.check(NameField.DELIVERY_DEADLINE, futureDate);
        assertTrue(result);
    }

    @Test
    void testCheck_DeliveryDeadline_InvalidPast() {
        String pastDate = LocalDate.now().minusDays(1).toString();
        boolean result = Validator.check(NameField.DELIVERY_DEADLINE, pastDate);
        assertFalse(result);
    }

    @Test
    void testCheck_DestinationCountry_Valid() {
        boolean result = Validator.check(NameField.DESTINATION_COUNTRY, "USA");
        assertTrue(result);
    }

    @Test
    void testCheck_DestinationCountry_Invalid() {
        boolean result = Validator.check(NameField.DESTINATION_COUNTRY, "Mars");
        assertFalse(result);
    }
}
