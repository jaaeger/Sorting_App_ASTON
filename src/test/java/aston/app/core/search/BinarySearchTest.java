package aston.app.core.search;

import aston.app.core.search.BinarySearch;
import aston.app.entity.ExpressParcel;
import aston.app.entity.InternationalParcel;
import aston.app.entity.Parcel;
import aston.app.entity.StandardParcel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BinarySearchTest {

    private List<Parcel> demoParcels() {
        List<Parcel> list = new ArrayList<>();
        list.add(new StandardParcel("Анна", 2.5, 101, 40));
        list.add(new ExpressParcel("Борис", 1.2, 55, LocalDate.parse("2025-10-30")));
        list.add(new InternationalParcel("Виктор", 3.0, 77, "США"));
        list.add(new StandardParcel("Галина", 0.8, 12, 35));
        return list;
    }

    @Test
    void when_sortedByTracking_searchExisting_then_indexFound() {
        List<Parcel> list = demoParcels();
        list.sort(Comparator.comparingInt(Parcel::getTrackingNumber));

        int index = BinarySearch.searchByTrackingNumber(list, 55);

        assertTrue(index >= 0, "Ожидаем, что индекс найден");
        assertEquals(55, list.get(index).getTrackingNumber());
    }

    @Test
    void when_sortedByTracking_searchMissing_then_negativeInsertionPoint() {
        List<Parcel> list = demoParcels();
        list.sort(Comparator.comparingInt(Parcel::getTrackingNumber));

        int index = BinarySearch.searchByTrackingNumber(list, 56);

        assertTrue(index < 0, "Ожидаем отрицательную точку вставки");
        int insertionPoint = -index - 1;
        assertEquals(2, insertionPoint);
    }

    @Test
    void when_sortedByRecipient_searchExisting_then_indexFound() {
        List<Parcel> list = demoParcels();
        list.sort(Comparator.comparing(Parcel::getRecipientName));

        int index = BinarySearch.searchByRecipient(list, "Виктор");

        assertTrue(index >= 0);
        assertEquals("Виктор", list.get(index).getRecipientName());
    }

    @Test
    void when_sortedByWeight_searchExisting_then_indexFound() {
        List<Parcel> list = demoParcels();
        list.sort(Comparator.comparingDouble(Parcel::getWeight));

        int index = BinarySearch.searchByWeight(list, 1.2);

        assertTrue(index >= 0);
        assertEquals(1.2, list.get(index).getWeight(), 1e-9);
    }

    @Test
    void when_sortedStandardByMaxDimension_searchExisting_then_indexFound() {
        List<StandardParcel> list = new ArrayList<>();
        list.add(new StandardParcel("А", 1.0, 1, 35));
        list.add(new StandardParcel("Б", 1.1, 2, 40));
        list.add(new StandardParcel("В", 1.2, 3, 45));

        list.sort(Comparator.comparingInt(StandardParcel::getMaxDimension));

        int index = BinarySearch.searchStandardByMaxDimension(list, 40);

        assertTrue(index >= 0);
        assertEquals(40, list.get(index).getMaxDimension());
    }

    @Test
    void when_sortedInternationalByCountry_searchExisting_then_indexFound() {
        List<InternationalParcel> list = new ArrayList<>();
        list.add(new InternationalParcel("А", 1.0, 1, "Бразилия"));
        list.add(new InternationalParcel("Б", 1.1, 2, "США"));
        list.add(new InternationalParcel("В", 1.2, 3, "Испания"));

        list.sort(Comparator.comparing(InternationalParcel::getDestinationCountry));

        int index = BinarySearch.searchInternationalByCountry(list, "США");

        assertTrue(index >= 0);
        assertEquals("США", list.get(index).getDestinationCountry());
    }

    @Test
    void when_sortedExpressByDeadline_searchExisting_then_indexFound() {
        List<ExpressParcel> list = new ArrayList<>();
        list.add(new ExpressParcel("А", 1.0, 1, LocalDate.parse("2025-10-25")));
        list.add(new ExpressParcel("Б", 1.1, 2, LocalDate.parse("2025-11-01")));
        list.add(new ExpressParcel("В", 1.2, 3, LocalDate.parse("2025-12-01")));

        list.sort(Comparator.comparing(ExpressParcel::getDeliveryDeadline));

        int index = BinarySearch.searchExpressByDeadline(list, LocalDate.parse("2025-11-01"));

        assertTrue(index >= 0);
        assertEquals(LocalDate.parse("2025-11-01"), list.get(index).getDeliveryDeadline());
    }
}
