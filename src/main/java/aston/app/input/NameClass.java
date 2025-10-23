package aston.app.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NameClass {
    PARCEL("PARCEL"),
    EXPRESS("EXPRESS"),
    INTERNATIONAL("INTERNATIONAL"),
    STANDARD("STANDARD");

    private final String nameClass;
}
