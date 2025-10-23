package aston.app.sorting;

import aston.app.entity.Parcel;

import java.util.Comparator;

public final class ParcelComparators {
    public static <T extends Parcel> Comparator<T> byRecipientName() {
        return Comparator.comparing(Parcel::getRecipientName);
    }

    public static <T extends Parcel> Comparator<T> byWeight() {
        return Comparator.comparing(Parcel::getWeight);
    }

    public static <T extends Parcel> Comparator<T> byTrackingNumber() {
        return Comparator.comparing(Parcel::getTrackingNumber);
    }

    public static Comparator<Parcel> byRecipientNameDesc() {
        return Comparator.comparing(Parcel::getRecipientName).reversed();
    }

    public static Comparator<Parcel> byWeightDesc() {
        return Comparator.comparing(Parcel::getWeight).reversed();
    }

    public static Comparator<Parcel> byTrackingNumberDesc() {
        return Comparator.comparing(Parcel::getTrackingNumber).reversed();
    }

    private ParcelComparators() {}
}
