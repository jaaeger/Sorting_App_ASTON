package aston.app.sorting;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public final class SortingClasses {
    private static final int POOL_SIZE = 4;

    public static <T> void sort(String type, List<T> list, Comparator<T> comparator) {
        ForkJoinTask<Void> task = getTask(type, list, comparator);

        ForkJoinPool pool = new ForkJoinPool(POOL_SIZE);
        try {
            pool.invoke(task);
        } finally {
            pool.shutdown();
        }
    }

    private static <T> ForkJoinTask<Void> getTask(String type, List<T> list, Comparator<T> comparator) {
        return switch (type) {
            case "quickSort" -> new QuickSortTask<>(list, comparator, 0, list.size() - 1);
            case "mergeSort" -> new MergeSortTask<>(list, comparator, 0, list.size() - 1);
            default -> throw new IllegalArgumentException("Unknown algorithm " + type);
        };
    }


    private SortingClasses() {}
}

