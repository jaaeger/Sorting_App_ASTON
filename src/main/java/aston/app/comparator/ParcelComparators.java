package aston.app.comparator;

import aston.app.entity.ExpressParcel;
import aston.app.entity.InternationalParcel;
import aston.app.entity.Parcel;
import aston.app.entity.StandardParcel;

import java.util.Comparator;

public final class ParcelComparators {
    public static <T extends Parcel> Comparator<T> byRecipientName() {
        return Comparator.comparing(
                Parcel::getRecipientName,
                Comparator.nullsLast(String::compareToIgnoreCase)
        );
    }

    public static <T extends Parcel> Comparator<T> byWeight() {
        return Comparator.comparingDouble(Parcel::getWeight);
    }

    public static <T extends Parcel> Comparator<T> byTrackingNumber() {
        return  Comparator.comparingInt(Parcel::getTrackingNumber);
    }

    public static Comparator<StandardParcel> byMaxDimension() {
        return Comparator.comparing(
                (Parcel p) -> (p instanceof StandardParcel sp) ? sp.getMaxDimension() : null,
                Comparator.nullsLast(Integer::compareTo)
        );
    }

    public static Comparator<InternationalParcel> byDestinationCountry() {
        return Comparator.comparing(
                (Parcel p) -> (p instanceof InternationalParcel ip) ? ip.getDestinationCountry() : null,
                Comparator.nullsLast(String::compareToIgnoreCase)
        )
                ;
    }

    public static Comparator<ExpressParcel> byDeliveryDeadline() {
        return  Comparator.comparing(
                (Parcel p) -> (p instanceof ExpressParcel ep) ? ep.getDeliveryDeadline() : null,
                Comparator.nullsLast(java.time.LocalDate::compareTo)
        );
    }

    private ParcelComparators() {
    }
}
