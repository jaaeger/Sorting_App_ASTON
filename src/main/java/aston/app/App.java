package aston.app;

import aston.app.entity.ExpressParcel;
import aston.app.entity.InternationalParcel;
import aston.app.entity.Parcel;
import aston.app.entity.StandardParcel;
import aston.app.input.NameField;
import aston.app.ui.ConsoleUI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        List<Parcel> list = new ArrayList<>();
        list.add(new StandardParcel("Анна", 2.5, 101, 40));
        list.add(new ExpressParcel("Борис", 1.2, 55, LocalDate.parse("2025-10-30")));
        list.add(new InternationalParcel("Виктор", 3.0, 77, "USA"));
        list.add(new StandardParcel("Галина", 4.1, 5, 75));
        list.add(new InternationalParcel("Дмитрий", 0.9, 500, "DE"));


        Map<NameField, Comparator<? super Parcel>> comparators = new EnumMap<>(NameField.class);

        comparators.put(
                NameField.RECIPIENT_NAME,
                Comparator.comparing(
                        Parcel::getRecipientName,
                        Comparator.nullsLast(String::compareToIgnoreCase)
                )
        );
        comparators.put(
                NameField.WEIGHT,
                Comparator.comparingDouble(Parcel::getWeight)
        );
        comparators.put(
                NameField.TRACKING_NUMBER,
                Comparator.comparingInt(Parcel::getTrackingNumber)
        );


        comparators.put(
                NameField.MAX_DIMENSION,
                Comparator.comparing(
                        (Parcel p) -> (p instanceof StandardParcel sp) ? sp.getMaxDimension() : null,
                        Comparator.nullsLast(Integer::compareTo)
                )
        );

        comparators.put(
                NameField.DESTINATION_COUNTRY,
                Comparator.comparing(
                        (Parcel p) -> (p instanceof InternationalParcel ip) ? ip.getDestinationCountry() : null,
                        Comparator.nullsLast(String::compareToIgnoreCase)
                )
        );

        comparators.put(
                NameField.DELIVERY_DEADLINE,
                Comparator.comparing(
                        (Parcel p) -> (p instanceof ExpressParcel ep) ? ep.getDeliveryDeadline() : null,
                        Comparator.nullsLast(java.time.LocalDate::compareTo)
                )
        );

        ConsoleUI ui = new ConsoleUI(new Scanner(System.in), list, comparators);
        ui.run();
    }
}