package aston.app.ui;

import aston.app.entity.Parcel;
import aston.app.input.model.NameField;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleContext {
    private final Scanner in;
    private final List<Parcel> parcels;
    private final Map<NameField, Comparator<? super Parcel>> comparators;

    public ConsoleContext(Scanner in,
                          List<Parcel> parcels,
                          Map<NameField, Comparator<? super Parcel>> comparators) {
        this.in = in;
        this.parcels = parcels;
        this.comparators = new EnumMap<>(NameField.class);
        this.comparators.putAll(comparators);
    }

    public Scanner in() {
        return in;
    }

    public List<Parcel> parcels() {
        return parcels;
    }

    public Map<NameField, Comparator<? super Parcel>> comparators() {
        return comparators;
    }
}
