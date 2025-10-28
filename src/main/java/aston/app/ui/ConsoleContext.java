package aston.app.ui;

import aston.app.entity.Parcel;
import aston.app.input.model.NameField;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public record ConsoleContext(Scanner in, List<Parcel> parcels, Map<NameField, Comparator<? super Parcel>> comparators) {
    public ConsoleContext(Scanner in,
                          List<Parcel> parcels,
                          Map<NameField, Comparator<? super Parcel>> comparators) {
        this.in = in;
        this.parcels = parcels;
        this.comparators = new EnumMap<>(NameField.class);
        this.comparators.putAll(comparators);
    }
}
