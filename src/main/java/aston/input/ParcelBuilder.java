package aston.input;

public class ParcelBuilder {

    private String recipientName;
    private double weight;
    private int trackingNumber;
    private int priorityLevel;
    private String countryCode;

    public ParcelBuilder setRecipientName(String recipientName) {
        this.recipientName = recipientName;
        return this;
    }

    public ParcelBuilder setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public ParcelBuilder setTrackingNumber(int trackingNumber) {
        this.trackingNumber = trackingNumber;
        return this;
    }

    public ParcelBuilder setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
        return this;
    }

    public ParcelBuilder setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public Parcel build(String type) {
        switch (type.toUpperCase()) {
            case "STANDARD":
                return new StandardParcel(recipientName, weight, trackingNumber);
            case "EXPRESS":
                return new ExpressParcel(recipientName, weight, trackingNumber, priorityLevel);
            case "INTERNATIONAL":
                return new InternationalParcel(recipientName, weight, trackingNumber, countryCode);
            default:
                throw new IllegalArgumentException("Unknown parcel type: " + type);
        }
    }
}