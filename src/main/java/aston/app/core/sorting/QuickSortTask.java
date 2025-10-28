package aston.app.core.sorting;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.RecursiveAction;

@RequiredArgsConstructor
public class QuickSortTask<T> extends RecursiveAction {
    private final List<T> list;
    private final Comparator<? super T> comparator;
    private final int low;
    private final int high;

    @Override
    protected void compute() {
        if (low < high) {
            int pivotIndex = partition(list, comparator, low, high);
            QuickSortTask<T> quickSortTask1 = new QuickSortTask<>(list, comparator, low, pivotIndex - 1);
            QuickSortTask<T> quickSortTask2 = new QuickSortTask<>(list, comparator, pivotIndex + 1, high);
            invokeAll(quickSortTask1, quickSortTask2);
        }
    }

    private static <T> int partition(List<T> l, Comparator<? super T> c, int low, int high) {
        int pivotIndex = low + (high - low) / 2;
        T pivot = l.get(pivotIndex);
        Collections.swap(l, pivotIndex, low);

        int left = low + 1;
        int right = high;

        while (true) {
            while (left <= right && c.compare(l.get(left), pivot) <= 0) {
                left++;
            }
            while (right >= left && c.compare(l.get(right), pivot) >= 0) {
                right--;
            }
            if (right < left) {
                break;
            } else {
                Collections.swap(l, left, right);
            }
        }
        Collections.swap(l, low, right);
        return right;
    }
}
