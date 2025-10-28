package aston.app.entity;

import aston.app.input.model.NameField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@EqualsAndHashCode(callSuper = true)
public class InternationalParcel extends Parcel {
    private final String destinationCountry;

    public InternationalParcel(String recipientName, double weight, int trackingNumber, String destinationCountry) {
        super(recipientName, weight, trackingNumber);
        this.destinationCountry = destinationCountry;
    }

    public static InternationalBuilder builder() {
        return new InternationalBuilder();
    }

    public static class InternationalBuilder extends ParcelBuilder<InternationalParcel, InternationalBuilder> {
        private String destinationCountry;

        public InternationalBuilder destinationCountry(String destinationCountry) {
            this.destinationCountry = destinationCountry;
            return this;
        }

        @Override
        public InternationalParcel build() {
            return new InternationalParcel(this.recipientName, this.weight, this.trackingNumber, this.destinationCountry);
        }

        @Override
        protected InternationalBuilder self() {
            return this;
        }
    }

    public static InternationalParcel fromMap(Map<String, String> data) {
        return InternationalParcel.builder()
                .recipientName(data.get("recipientName"))
                .weight(Double.parseDouble(data.get("weight")))
                .trackingNumber(Integer.parseInt(data.get("trackingNumber")))
                .destinationCountry(data.get("destinationCountry"))
                .build();
    }

    public String toString() {
        return "Международная посылка {\n" +
                super.toString() +
                "\t" + NameField.DESTINATION_COUNTRY.getDescription() + " = " + this.getDestinationCountry() + "\n}";
    }
}
