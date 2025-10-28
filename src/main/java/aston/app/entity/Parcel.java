package aston.app.entity;

import aston.app.input.model.NameClass;
import aston.app.input.model.NameField;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;

@Getter
@EqualsAndHashCode
public abstract class Parcel implements Comparable<Parcel> {
    protected final String recipientName;
    protected final double weight;
    protected final int trackingNumber;

    protected Parcel(String recipientName, double weight, int trackingNumber) {
        this.recipientName = recipientName;
        this.weight = weight;
        this.trackingNumber = trackingNumber;
    }

    public static abstract class ParcelBuilder<T extends Parcel, B extends ParcelBuilder<T, B>> {
        protected String recipientName;
        protected double weight;
        protected int trackingNumber;

        ParcelBuilder() {
        }

        public B recipientName(String recipientName) {
            this.recipientName = recipientName;
            return self();
        }

        public B weight(double weight) {
            this.weight = weight;
            return self();
        }

        public B trackingNumber(int trackingNumber) {
            this.trackingNumber = trackingNumber;
            return self();
        }

        public abstract T build();

        protected abstract B self();
    }

    public static Parcel createFromMap(Map<String, String> data) {
        String type = data.get("type");
        Function<Map<String, String>, Parcel> factory = NameClass.getFactory(type);

        return factory.apply(data);
    }

    @Override
    public int compareTo(Parcel v) {
        int recipientNameCompare = this.recipientName.compareTo(v.recipientName);
        if (recipientNameCompare != 0) {
            return recipientNameCompare;
        }

        int weightCompare = Double.compare(this.weight, v.weight);
        if (weightCompare != 0) {
            return weightCompare;
        }

        return Integer.compare(this.trackingNumber, v.trackingNumber);
    }

    public String toString() {
        return  "\t" +NameField.RECIPIENT_NAME.getDescription() + " = " + this.getRecipientName() + "\n" +
                "\t" +NameField.WEIGHT.getDescription() + " = " + this.getWeight() + "\n" +
                "\t" +NameField.TRACKING_NUMBER.getDescription() + " = " + this.getTrackingNumber() + "\n";
    }
}
