package aston.app.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;

public final class SortingClasses {
    private static final int POOL_SIZE = 4;

    public static <T> void sortList(String type, List<T> list, Comparator<T> comparator) {
        ForkJoinTask<Void> task = getTask(type, list, comparator);

        ForkJoinPool pool = new ForkJoinPool(POOL_SIZE);
        try {
            pool.invoke(task);
        } finally {
            pool.shutdown();
        }
    }

    public static <K, V> List<Map.Entry<K, V>> sortMapByValue(String type, Map<K, V> map, Comparator<V> comparator) {
        List<Map.Entry<K, V>> entries = new ArrayList<>(map.entrySet());

        sortList(type, entries, Map.Entry.comparingByValue(comparator));
        return entries;
    }

    public static <K, V> List<Map.Entry<K, V>> sortMapByKey(String type, Map<K, V> map, Comparator<K> comparator) {
        List<Map.Entry<K, V>> entries = new ArrayList<>(map.entrySet());

        sortList(type, entries, Map.Entry.comparingByKey(comparator));
        return entries;
    }

    public static <T> void sortByNumericField(String type, List<T> list, Comparator<T> comparator,
                                              Function<T, Integer> getField) {
        Map<Integer, T> map = new HashMap<>();
        List<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            Integer fieldValue = getField.apply(t);

            if (fieldValue % 2 == 0) {
                indexList.add(i);
                map.put(i, t);
            }
        }

        List<Map.Entry<Integer, T>> sortedMap = sortMapByValue(type, map, comparator);

        int j = 0;
        for (int i = 0; i < sortedMap.size(); i++) {
            if (indexList.get(j) == i) {
                Map.Entry<Integer, T> element = sortedMap.get(i);
                list.set(i, element.getValue());
                j++;
            }
        }
    }

    private static <T> ForkJoinTask<Void> getTask(String type, List<T> list, Comparator<T> comparator) {
        return switch (type) {
            case "quickSort" -> new QuickSortTask<>(list, comparator, 0, list.size() - 1);
            case "mergeSort" -> new MergeSortTask<>(list, comparator, 0, list.size() - 1);
            default -> throw new IllegalArgumentException("Unknown algorithm " + type);
        };
    }

    private SortingClasses() {
    }
}

