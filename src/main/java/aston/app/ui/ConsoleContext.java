package aston.app.ui;

import aston.app.entity.Parcel;

import java.util.List;
import java.util.Scanner;

public record ConsoleContext(Scanner in, List<Parcel> parcels) {
}
