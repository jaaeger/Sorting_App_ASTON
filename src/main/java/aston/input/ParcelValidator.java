package aston.input;

public class ParcelValidator {
    public static void validate(aston.app.entity.Parcel parcel) {
        if (parcel.getRecipientName() == null || parcel.getRecipientName().isBlank()) {
            throw new IllegalArgumentException("Recipient name cannot be empty");
        }
        if (parcel.getWeight() <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }
        if (parcel.getTrackingNumber() <= 0) {
            throw new IllegalArgumentException("Tracking number must be positive");
        }
    }
}
