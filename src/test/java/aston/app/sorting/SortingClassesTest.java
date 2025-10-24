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
    public static void setUpAll() {
        parcel1 = createParcel("12");
        parcel2 = createParcel("10");
        parcel3 = createParcel("7");
        parcel4 = createParcel("11");
    }

    private static Parcel createParcel(String weight) {
        Map<String, String> data = new HashMap<>();
        data.put("type", "STANDARD");
        data.put("recipientName", "user1");
        data.put("weight", weight);
        data.put("trackingNumber", "5323");
        data.put("maxDimension", "200");
        return Parcel.createFromMap(data);
    }

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        list.add(parcel1);
        list.add(parcel2);
        list.add(parcel3);
        list.add(parcel4);
    }

    @Test
    public void when_QuickSortByWeight_then_ParcelsSortedByWeightAscending() {
        SortingClasses.sort("quickSort", list, ParcelComparators.byWeight());

        Assertions.assertEquals(parcel3, list.get(0));
        Assertions.assertEquals(parcel2, list.get(1));
        Assertions.assertEquals(parcel4, list.get(2));
        Assertions.assertEquals(parcel1, list.get(3));
    }

    @Test
    public void when_MergeSortByWeight_then_ParcelsSortedByWeightAscending() {
        SortingClasses.sort("mergeSort", list, ParcelComparators.byWeight());

        Assertions.assertEquals(parcel3, list.get(0));
        Assertions.assertEquals(parcel2, list.get(1));
        Assertions.assertEquals(parcel4, list.get(2));
        Assertions.assertEquals(parcel1, list.get(3));
    }

    @Test
    public void when_QuickSortNaturalOrder_then_ParcelsSortedByNaturalOrder() {
        SortingClasses.sort("quickSort", list, Comparator.naturalOrder());

        Assertions.assertEquals(parcel3, list.get(0));
        Assertions.assertEquals(parcel2, list.get(1));
        Assertions.assertEquals(parcel4, list.get(2));
        Assertions.assertEquals(parcel1, list.get(3));
    }

    @Test
    public void when_MergeSortNaturalOrder_then_ParcelsSortedByNaturalOrder() {
        SortingClasses.sort("mergeSort", list, Comparator.naturalOrder());

        Assertions.assertEquals(parcel3, list.get(0));
        Assertions.assertEquals(parcel2, list.get(1));
        Assertions.assertEquals(parcel4, list.get(2));
        Assertions.assertEquals(parcel1, list.get(3));
    }
}
