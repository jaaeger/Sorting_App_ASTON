package aston.app.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ExpressParcel extends Parcel {
    private final LocalDateTime deliveryDeadline;

    public ExpressParcel(String recipientName, double weight, int trackingNumber, LocalDateTime deliveryDeadline) {
        super(recipientName, weight, trackingNumber);

        this.deliveryDeadline = deliveryDeadline;
    }

    public static ExpressBuilder builder() {
        return new ExpressBuilder();
    }

    public static class ExpressBuilder extends ParcelBuilder<ExpressParcel, ExpressBuilder> {
        private LocalDateTime deliveryDeadline;

        public ExpressBuilder deliveryDeadline(LocalDateTime deliveryDeadline) {
            this.deliveryDeadline = deliveryDeadline;
            return this;
        }

        @Override
        public ExpressParcel build() {
            return new ExpressParcel(this.recipientName, this.weight, this.trackingNumber, this.deliveryDeadline);
        }

        @Override
        protected ExpressBuilder self() {
            return this;
        }
    }

    public static ExpressParcel fromMap(Map<String, String> data) {
        return ExpressParcel.builder()
                .recipientName(data.get("recipientName"))
                .weight(Double.parseDouble(data.get("weight")))
                .trackingNumber(Integer.parseInt(data.get("trackingNumber")))
                .deliveryDeadline(LocalDateTime.parse(data.get("deliveryDeadline")))
                .build();
    }
}
