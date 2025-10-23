package aston.app.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StandardParcel extends Parcel {
    private final int maxDimension;

    public StandardParcel(String recipientName, double weight, int trackingNumber, int maxDimension) {
        super(recipientName, weight, trackingNumber);

        this.maxDimension = maxDimension;
    }

    public static StandardBuilder builder() {
        return new StandardBuilder();
    }

    public static class StandardBuilder extends ParcelBuilder<StandardParcel, StandardBuilder> {
        private int maxDimension;

        public StandardBuilder maxDimension(int maxDimension) {
            this.maxDimension = maxDimension;
            return this;
        }

        @Override
        public StandardParcel build() {
            return new StandardParcel(this.recipientName, this.weight, this.trackingNumber, this.maxDimension);
        }

        @Override
        protected StandardBuilder self() {
            return this;
        }
    }

    public static StandardParcel fromMap(Map<String, String> data) {
        return StandardParcel.builder()
                .recipientName(data.get("recipientName"))
                .weight(Double.parseDouble(data.get("weight")))
                .trackingNumber(Integer.parseInt(data.get("trackingNumber")))
                .maxDimension(Integer.parseInt(data.get("maxDimension")))
                .build();
    }
}
