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
    private static Parcel parcel1, parcel2, parcel3, parcel4, parcel5;

    @BeforeAll
    public static void setUpAll() {
        parcel1 = createParcel("12", "1212");
        parcel2 = createParcel("10", "1010");
        parcel3 = createParcel("7", "777");
        parcel4 = createParcel("11","1111");
        parcel5 = createParcel("14","1414");
    }

    private static Parcel createParcel(String weight, String trackingNumber) {
        Map<String, String> data = new HashMap<>();
        data.put("type", "STANDARD");
        data.put("recipientName", "user1");
        data.put("weight", weight);
        data.put("trackingNumber", trackingNumber);
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
        list.add(parcel5);
    }

    @Test
    public void when_QuickSortByWeight_then_ParcelsSortedByWeightAscending() {
        SortingClasses.sortList("quickSort", list, ParcelComparators.byWeight());

        Assertions.assertEquals(parcel3, list.get(0));
        Assertions.assertEquals(parcel2, list.get(1));
        Assertions.assertEquals(parcel4, list.get(2));
        Assertions.assertEquals(parcel1, list.get(3));
        Assertions.assertEquals(parcel5, list.get(4));
    }

    @Test
    public void when_MergeSortByWeight_then_ParcelsSortedByWeightAscending() {
        SortingClasses.sortList("mergeSort", list, ParcelComparators.byWeight());

        Assertions.assertEquals(parcel3, list.get(0));
        Assertions.assertEquals(parcel2, list.get(1));
        Assertions.assertEquals(parcel4, list.get(2));
        Assertions.assertEquals(parcel1, list.get(3));
        Assertions.assertEquals(parcel5, list.get(4));
    }

    @Test
    public void when_QuickSortNaturalOrder_then_ParcelsSortedByNaturalOrder() {
        SortingClasses.sortList("quickSort", list, Comparator.naturalOrder());

        Assertions.assertEquals(parcel3, list.get(0));
        Assertions.assertEquals(parcel2, list.get(1));
        Assertions.assertEquals(parcel4, list.get(2));
        Assertions.assertEquals(parcel1, list.get(3));
        Assertions.assertEquals(parcel5, list.get(4));
    }

    @Test
    public void when_MergeSortNaturalOrder_then_ParcelsSortedByNaturalOrder() {
        SortingClasses.sortList("mergeSort", list, Comparator.naturalOrder());

        Assertions.assertEquals(parcel3, list.get(0));
        Assertions.assertEquals(parcel2, list.get(1));
        Assertions.assertEquals(parcel4, list.get(2));
        Assertions.assertEquals(parcel1, list.get(3));
        Assertions.assertEquals(parcel5, list.get(4));
    }

    @Test
    public void when_QuickSortByNumericField_then_ParcelSortedByNumericField() {
        SortingClasses.sortByNumericField("quickSort", list, ParcelComparators.byWeight(), Parcel::getTrackingNumber);

        Assertions.assertEquals(parcel2, list.get(0));
        Assertions.assertEquals(parcel1, list.get(1));
        Assertions.assertEquals(parcel3, list.get(2));
        Assertions.assertEquals(parcel4, list.get(3));
        Assertions.assertEquals(parcel5, list.get(4));
    }
}
