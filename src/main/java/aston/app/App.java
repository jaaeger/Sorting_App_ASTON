package aston.app;

import aston.app.entity.ExpressParcel;
import aston.app.entity.InternationalParcel;
import aston.app.entity.Parcel;
import aston.app.entity.StandardParcel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static aston.app.search.BinarySearch.searchByTrackingNumber;

public class App {
    public static void main(String[] args) {

        List<Parcel> list = new ArrayList<>();
        list.add(new StandardParcel("Анна", 2.5, 101, 40));
        list.add(new ExpressParcel("Борис", 1.2, 55, LocalDate.parse("2025-10-30")));
        list.add(new InternationalParcel("Виктор", 3.0, 77, "USA"));

        list.sort(Comparator.comparingInt(Parcel::getTrackingNumber));

        int index = searchByTrackingNumber(list, 55);

        if (index >= 0) {
            System.out.println("Посылка найдена: " + list.get(index));
        } else {
            System.out.println("Посылка не найдена. Можно вставить на позицию: " + (-index - 1));
        }
    }
}
