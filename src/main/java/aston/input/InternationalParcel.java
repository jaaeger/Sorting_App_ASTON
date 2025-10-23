package aston.input;

public class InternationalParcel extends Parcel {
    private final String countryCode;

    public InternationalParcel(String recipientName, double weight, int trackingNumber, String countryCode) {
        super(recipientName, weight, trackingNumber);
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public String toString() {
        return super.toString() + ", countryCode='" + countryCode + "'";
    }
}