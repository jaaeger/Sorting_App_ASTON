package aston.app.entity;

public abstract class Parcel implements Comparable<Parcel> {
    protected final String recipientName;
    protected final double weight;
    protected final int trackingNumber;

    protected Parcel(String recipientName, double weight, int trackingNumber) {
        this.recipientName = recipientName;
        this.weight = weight;
        this.trackingNumber = trackingNumber;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public double getWeight() {
        return weight;
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    @Override
    public int compareTo(Parcel other) {
        int cmp = recipientName.compareTo(other.recipientName);
        if (cmp != 0) return cmp;
        cmp = Double.compare(weight, other.weight);
        if (cmp != 0) return cmp;
        return Integer.compare(trackingNumber, other.trackingNumber);
    }

    @Override
    public String toString() {
        return String.format("%s{name='%s', weight=%.2f, trackingNumber=%d}",
                getClass().getSimpleName(), recipientName, weight, trackingNumber);
    }
}
