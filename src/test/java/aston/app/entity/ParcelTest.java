package aston.app.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ParcelTest {
    public static Map<String, String> data;

    @BeforeEach
    public void setUp() {
        data = new HashMap<>();
        data.put("recipientName", "user1");
        data.put("weight", "12.5");
        data.put("trackingNumber", "5323");
    }

    @Test
    public void when_CreateStandardParcelWithMaxDimension_then_ParcelCreated() {
        data.put("type", "STANDARD");
        data.put("maxDimension", "200");

        StandardParcel parcel = (StandardParcel) Parcel.createFromMap(data);

        Assertions.assertEquals("user1", parcel.recipientName);
        Assertions.assertEquals(12.5, parcel.weight);
        Assertions.assertEquals(5323, parcel.trackingNumber);
        Assertions.assertEquals(200, parcel.getMaxDimension());
    }

    @Test
    public void when_CreateExpressParcelWithDeliveryDeadline_then_ExpressParcelCreated() {
        data.put("type", "EXPRESS");
        data.put("deliveryDeadline", "2024-01-15");

        ExpressParcel parcel = (ExpressParcel) Parcel.createFromMap(data);

        Assertions.assertEquals("user1", parcel.recipientName);
        Assertions.assertEquals(12.5, parcel.weight);
        Assertions.assertEquals(5323, parcel.trackingNumber);
        Assertions.assertEquals(LocalDate.of(2024, 1, 15), parcel.getDeliveryDeadline());
    }

    @Test
    public void when_CreateInternationalParcelWithDestinationCountry_then_InternationalParcelCreated() {
        data.put("type", "INTERNATIONAL");
        data.put("destinationCountry", "Англия");

        InternationalParcel parcel = (InternationalParcel) Parcel.createFromMap(data);

        Assertions.assertEquals("user1", parcel.recipientName);
        Assertions.assertEquals(12.5, parcel.weight);
        Assertions.assertEquals(5323, parcel.trackingNumber);
        Assertions.assertEquals("Англия", parcel.getDestinationCountry());
    }

    @Test
    void when_CreateParcelWithInvalidType_then_ThrowException() {
        data.put("type", "UNKNOWN_TYPE");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Parcel.createFromMap(data);
        });
    }
}
