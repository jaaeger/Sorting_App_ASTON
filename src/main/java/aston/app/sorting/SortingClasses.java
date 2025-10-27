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
        validateSortingParameters(type, list, comparator);

        if (list.isEmpty()) {
            return;
        }

        ForkJoinTask<Void> task = getTask(type, list, comparator);

        ForkJoinPool pool = new ForkJoinPool(POOL_SIZE);
        try {
            pool.invoke(task);
        } finally {
            pool.shutdown();
        }
    }

    public static <K, V> List<Map.Entry<K, V>> sortMapByValue(String type, Map<K, V> map, Comparator<V> comparator) {
        validateMapParameters(type, map, comparator);

        List<Map.Entry<K, V>> entries = new ArrayList<>(map.entrySet());

        if (entries.isEmpty()) {
            return entries;
        }

        sortList(type, entries, Map.Entry.comparingByValue(comparator));
        return entries;
    }

    public static <K, V> List<Map.Entry<K, V>> sortMapByKey(String type, Map<K, V> map, Comparator<K> comparator) {
        validateMapParameters(type, map, comparator);

        List<Map.Entry<K, V>> entries = new ArrayList<>(map.entrySet());

        if (entries.isEmpty()) {
            return entries;
        }

        sortList(type, entries, Map.Entry.comparingByKey(comparator));
        return entries;
    }

    public static <T> void sortByNumericField(String type, List<T> list, Comparator<T> comparator,
                                              Function<T, Integer> getField) {
        validateSortingParameters(type, list, comparator);

        if (getField == null) {
            throw new IllegalArgumentException("Функция извлечения поля не может быть null");
        }

        if (list.isEmpty()) {
            return;
        }

        Map<Integer, T> map = new HashMap<>();
        List<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            Integer fieldValue = getField.apply(t);

            if (fieldValue == null) {
                throw new IllegalArgumentException("Значение поля не может быть null для элемента: " + t);
            }

            if (fieldValue % 2 == 0) {
                indexList.add(i);
                map.put(i, t);
            }
        }

        if (map.isEmpty()) {
            return;
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

    private static <T> void validateSortingParameters(String type, List<T> list, Comparator<T> comparator) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип алгоритма не может быть null или пустым");
        }
        if (list == null) {
            throw new IllegalArgumentException("Список не может быть null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Компаратор не может быть null");
        }
    }

    private static <K, V> void validateMapParameters(String type, Map<K, V> map, Comparator<?> comparator) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип алгоритма не может быть null или пустым");
        }
        if (map == null) {
            throw new IllegalArgumentException("Карта не может быть null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Компаратор не может быть null");
        }
    }

    private static <T> ForkJoinTask<Void> getTask(String type, List<T> list, Comparator<T> comparator) {
        return switch (type.trim()) {
            case "quickSort" -> new QuickSortTask<>(list, comparator, 0, list.size() - 1);
            case "mergeSort" -> new MergeSortTask<>(list, comparator, 0, list.size() - 1);
            default -> throw new IllegalArgumentException("Неизвестный алгоритм: " + type);
        };
    }

    private SortingClasses() {
    }
}