package aston.app.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum NameField {
    RECIPIENT_NAME("recipientName", NameClass.PARCEL, "Имя получателя"),
    WEIGHT("weight", NameClass.PARCEL, "Вес"),
    TRACKING_NUMBER("trackingNumber", NameClass.PARCEL, "Номер отслеживания"),
    MAX_DIMENSION("maxDimension", NameClass.STANDARD, "Максимальный размер"),
    DESTINATION_COUNTRY("destinationCountry", NameClass.INTERNATIONAL, "Страна назначения"),
    DELIVERY_DEADLINE("deliveryDeadline", NameClass.EXPRESS, "Срок доставки");

    private final String nameField;
    private final NameClass nameClass;
    private final String description;

    public boolean ifForClass(String type) {
        return type.equals(nameClass.getNameClass()) || nameClass.getNameClass().equals(NameClass.PARCEL.getNameClass());
    }
}
