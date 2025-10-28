package aston.app;

import aston.app.entity.ExpressParcel;
import aston.app.entity.InternationalParcel;
import aston.app.entity.Parcel;
import aston.app.entity.StandardParcel;
import aston.app.ui.ConsoleUI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        List<Parcel> list = new ArrayList<>();
        list.add(new StandardParcel("Анна", 2.5, 101, 40));
        list.add(new ExpressParcel("Борис", 1.2, 55, LocalDate.parse("2025-10-30")));
        list.add(new InternationalParcel("Виктор", 3.0, 77, "USA"));
        list.add(new StandardParcel("Галина", 4.1, 5, 75));
        list.add(new InternationalParcel("Дмитрий", 0.9, 500, "DE"));

        ConsoleUI ui = new ConsoleUI(new Scanner(System.in), list);
        ui.run();
    }
}