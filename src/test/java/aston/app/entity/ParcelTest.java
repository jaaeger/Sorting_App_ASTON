package aston.app.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ParcelTest {
    public static Map<String, String> data;
    public static String recipientName, weight, trackingNumber, maxDimension, deliveryDeadline, destinationCountry;

    @BeforeAll
    public static void setupAll() {
        recipientName = "user1";
        weight = "12";
        trackingNumber = "5323";
        maxDimension = "200";
        deliveryDeadline = "2024-01-15";
        destinationCountry = "Англия";

        data = new HashMap<>();

        data.put("recipientName", recipientName);
        data.put("weight", weight);
        data.put("trackingNumber", trackingNumber);
    }

    @Test
    public void createStandardParcel() {
        data.put("type", "STANDARD");
        data.put("maxDimension", maxDimension);
        StandardParcel parcel = (StandardParcel) Parcel.createFromMap(data);

        Assertions.assertEquals(recipientName, parcel.recipientName);
        Assertions.assertEquals(Double.parseDouble(weight), parcel.weight);
        Assertions.assertEquals(Integer.parseInt(trackingNumber), parcel.trackingNumber);
        Assertions.assertEquals(Integer.parseInt(maxDimension), parcel.getMaxDimension());
    }

    @Test
    public void createExpressParcel() {
        data.put("type", "EXPRESS");
        data.put("deliveryDeadline", deliveryDeadline);
        ExpressParcel parcel = (ExpressParcel) Parcel.createFromMap(data);

        Assertions.assertEquals(recipientName, parcel.recipientName);
        Assertions.assertEquals(Double.parseDouble(weight), parcel.weight);
        Assertions.assertEquals(Integer.parseInt(trackingNumber), parcel.trackingNumber);
        Assertions.assertEquals(LocalDate.parse(deliveryDeadline), parcel.getDeliveryDeadline());
    }

    @Test
    public void createInternationalParcel() {
        data.put("type", "INTERNATIONAL");
        data.put("destinationCountry", destinationCountry);
        InternationalParcel parcel = (InternationalParcel) Parcel.createFromMap(data);

        Assertions.assertEquals(recipientName, parcel.recipientName);
        Assertions.assertEquals(Double.parseDouble(weight), parcel.weight);
        Assertions.assertEquals(Integer.parseInt(trackingNumber), parcel.trackingNumber);
        Assertions.assertEquals(destinationCountry, parcel.getDestinationCountry());
    }
}
