package aston.app.core.comparator;

import aston.app.entity.ExpressParcel;
import aston.app.entity.InternationalParcel;
import aston.app.entity.Parcel;
import aston.app.entity.StandardParcel;
import aston.app.input.model.NameField;

import java.time.LocalDate;
import java.util.Comparator;

public final class ParcelComparators {

    public static Comparator<? super Parcel> byNatural() {
        return Comparator.naturalOrder();
    }

    public static Comparator<? super Parcel> byRecipientName() {
        return Comparator.comparing(
                Parcel::getRecipientName,
                Comparator.nullsLast(String::compareToIgnoreCase)
        );
    }

    public static Comparator<? super Parcel> byWeight() {
        return Comparator.comparingDouble(Parcel::getWeight);
    }

    public static Comparator<? super Parcel> byTrackingNumber() {
        return Comparator.comparingInt(Parcel::getTrackingNumber);
    }

    public static Comparator<? super Parcel> byMaxDimension() {
        return Comparator.comparing(
                (Parcel p) -> (p instanceof StandardParcel sp) ? sp.getMaxDimension() : null,
                Comparator.nullsLast(Integer::compareTo)
        );
    }

    public static Comparator<? super Parcel> byDestinationCountry() {
        return Comparator.comparing(
                (Parcel p) -> (p instanceof InternationalParcel ip) ? ip.getDestinationCountry() : null,
                Comparator.nullsLast(String::compareToIgnoreCase)
        );
    }

    public static Comparator<? super Parcel> byDeliveryDeadline() {
        return Comparator.comparing(
                (Parcel p) -> (p instanceof ExpressParcel ep) ? ep.getDeliveryDeadline() : null,
                Comparator.nullsLast(LocalDate::compareTo)
        );
    }

    public static Comparator<? super Parcel> byField(NameField field) {
        return switch (field) {
            case RECIPIENT_NAME -> byRecipientName();
            case WEIGHT -> byWeight();
            case TRACKING_NUMBER -> byTrackingNumber();
            case MAX_DIMENSION -> byMaxDimension();
            case DESTINATION_COUNTRY -> byDestinationCountry();
            case DELIVERY_DEADLINE -> byDeliveryDeadline();
        };
    }

    private ParcelComparators() {
        }
    }
