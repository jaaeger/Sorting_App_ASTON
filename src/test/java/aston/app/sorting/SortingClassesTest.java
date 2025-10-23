package aston.app.sorting;

import aston.app.entity.Parcel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortingClassesTest {
    private List<Parcel> list;
    private static Parcel parcel1, parcel2, parcel3, parcel4;

    @BeforeAll
    public static void setupAll() {
        Map<String, String> data = new HashMap<>();

        data.put("type", "STANDARD");
        data.put("recipientName", "user1");
        data.put("weight", "12");
        data.put("trackingNumber", "5323");
        data.put("maxDimension", "200");
        parcel1 = Parcel.createFromMap(data);

        data.put("weight", "10");
        parcel2 = Parcel.createFromMap(data);

        data.put("weight", "7");
        parcel3 = Parcel.createFromMap(data);

        data.put("weight", "11");
        parcel4 = Parcel.createFromMap(data);
    }

    @BeforeEach
    public void setup() {
        list = new ArrayList<>();
        list.add(parcel1);
        list.add(parcel2);
        list.add(parcel3);
        list.add(parcel4);
    }

    @Test
    public void testQuickSortByWeight() {
        SortingClasses.quickSort(list, ParcelComparators.byWeight());

        Assertions.assertEquals(parcel3, list.get(0));
        Assertions.assertEquals(parcel2, list.get(1));
        Assertions.assertEquals(parcel4, list.get(2));
        Assertions.assertEquals(parcel1, list.get(3));
    }

    @Test
    public void testMergeSortByWeight() {
        SortingClasses.mergeSort(list, ParcelComparators.byWeight());

        Assertions.assertEquals(parcel3, list.get(0));
        Assertions.assertEquals(parcel2, list.get(1));
        Assertions.assertEquals(parcel4, list.get(2));
        Assertions.assertEquals(parcel1, list.get(3));
    }

    @Test
    public void testQuickSortNaturalOrder() {
        SortingClasses.quickSort(list, Comparator.naturalOrder());

        Assertions.assertEquals(parcel3, list.get(0));
        Assertions.assertEquals(parcel2, list.get(1));
        Assertions.assertEquals(parcel4, list.get(2));
        Assertions.assertEquals(parcel1, list.get(3));
    }

    @Test
    public void testMergeSortNaturalOrder() {
        SortingClasses.mergeSort(list, Comparator.naturalOrder());

        Assertions.assertEquals(parcel3, list.get(0));
        Assertions.assertEquals(parcel2, list.get(1));
        Assertions.assertEquals(parcel4, list.get(2));
        Assertions.assertEquals(parcel1, list.get(3));
    }
}
