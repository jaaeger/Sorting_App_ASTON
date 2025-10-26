package aston.app.search;

import aston.app.entity.ExpressParcel;
import aston.app.entity.InternationalParcel;
import aston.app.entity.Parcel;
import aston.app.entity.StandardParcel;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public final class BinarySearch {

    public static <T, K extends Comparable<? super K>> int search(List<T> list, K key, Function<T, K> keyExtractor) {
        int left = 0;
        int right = list.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            K midKey = keyExtractor.apply(list.get(mid));
            int c = midKey.compareTo(key);
            if (c == 0) {
                return mid;
            }
            if (c < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -(left + 1);
    }


    public static int searchByRecipient(List<? extends Parcel> parcels, String recipientName) {
        return search(parcels, recipientName, Parcel::getRecipientName);
    }

    public static int searchByWeight(List<? extends Parcel> parcels, double weight) {
        return search(parcels, weight, Parcel::getWeight);
    }

    public static int searchByTrackingNumber(List<? extends Parcel> parcels, int trackingNumber) {
        return search(parcels, trackingNumber, Parcel::getTrackingNumber);
    }

    public static int searchStandardByMaxDimension(List<StandardParcel> list, int maxDimension) {
        return search(list, maxDimension, StandardParcel::getMaxDimension);
    }

    public static int searchInternationalByCountry(List<InternationalParcel> list, String country) {
        return search(list, country, InternationalParcel::getDestinationCountry);
    }

    public static int searchExpressByDeadline(List<ExpressParcel> list, LocalDate deadline) {
        return search(list, deadline, ExpressParcel::getDeliveryDeadline);
    }
}
