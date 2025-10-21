package ru.aston.team.sortapp.input;

import java.util.Objects;

public class Parcel {
    private final String recipientName;
    private final double weight;
    private final int trackingNumber;

    private Parcel(Builder builder) {
        if (builder.recipientName == null || builder.recipientName.isBlank()) {
            throw new IllegalArgumentException("Recipient name cannot be empty");
        }
        if (builder.weight <= 0) {
            throw new IllegalArgumentException("Parcel weight must be greater than 0");
        }
        if (builder.trackingNumber <= 0) {
            throw new IllegalArgumentException("Tracking number must be positive");
        }

        this.recipientName = builder.recipientName;
        this.weight = builder.weight;
        this.trackingNumber = builder.trackingNumber;
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
    public String toString() {
        return String.format("Parcel{name='%s', weight=%.2f, trackingNumber=%d}",
                recipientName, weight, trackingNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parcel)) return false;
        Parcel parcel = (Parcel) o;
        return Double.compare(parcel.weight, weight) == 0 &&
                trackingNumber == parcel.trackingNumber &&
                recipientName.equals(parcel.recipientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipientName, weight, trackingNumber);
    }

    public static class Builder {
        private String recipientName;
        private double weight;
        private int trackingNumber;

        public Builder setRecipientName(String recipientName) {
            this.recipientName = recipientName;
            return this;
        }

        public Builder setWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder setTrackingNumber(int trackingNumber) {
            this.trackingNumber = trackingNumber;
            return this;
        }

        public Parcel build() {
            return new Parcel(this);
        }
    }
}
