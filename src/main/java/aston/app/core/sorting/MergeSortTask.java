package aston.app.core.sorting;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.RecursiveAction;

@RequiredArgsConstructor
public class MergeSortTask<T> extends RecursiveAction {
    private final List<T> list;
    private final Comparator<? super T> comparator;
    private final int left;
    private final int right;

    @Override
    protected void compute() {
        if (left < right) {
            int mid = (left + right) / 2;
            MergeSortTask<T> mergeSortTask1 = new MergeSortTask<>(list, comparator, left, mid);
            MergeSortTask<T> mergeSortTask2 = new MergeSortTask<>(list, comparator, mid + 1, right);
            invokeAll(mergeSortTask1, mergeSortTask2);
            merge(list, comparator, left, mid, right);
        }
    }

    private static <T> void merge(List<T> l, Comparator<? super T> c, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<T> L = new ArrayList<>(n1);
        List<T> R = new ArrayList<>(n2);

        for (int i = 0; i < n1; i++) {
            L.add(l.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            R.add(l.get(mid + 1 + j));
        }

        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (c.compare(L.get(i), R.get(j)) <= 0) {
                l.set(k, L.get(i));
                i++;
            } else {
                l.set(k, R.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            l.set(k, L.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            l.set(k, R.get(j));
            j++;
            k++;
        }
    }
}
