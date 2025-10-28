package aston.app.input.model;

import aston.app.entity.ExpressParcel;
import aston.app.entity.InternationalParcel;
import aston.app.entity.Parcel;
import aston.app.entity.StandardParcel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum NameClass {
    PARCEL("PARCEL"),
    EXPRESS("EXPRESS"),
    INTERNATIONAL("INTERNATIONAL"),
    STANDARD("STANDARD");

    private final String nameClass;

    public static Function<Map<String, String>, Parcel> getFactory(String name) {
        return switch (name.toUpperCase()) {
            case "PARCEL", "STANDARD" -> StandardParcel::fromMap;
            case "EXPRESS" -> ExpressParcel::fromMap;
            case "INTERNATIONAL" -> InternationalParcel::fromMap;
            default -> throw new IllegalArgumentException("Неизвестный тип посылки: " + name);
        };
    }
}
