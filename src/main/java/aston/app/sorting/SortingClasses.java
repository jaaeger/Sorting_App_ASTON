package aston.app.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class SortingClasses {
    public static <T> void quickSort(List<T> l, Comparator<T> c) {
        quickSort(l, c, 0, l.size() - 1);
    }

    public static <T> void quickSort(List<T> l, Comparator<T> c, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(l, c, low, high);
            quickSort(l, c, low, pivotIndex - 1);
            quickSort(l, c, pivotIndex + 1, high);
        }
    }

    private static <T> int partition(List<T> l, Comparator<T> c, int low, int high) {
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

    public static <T> void mergeSort(List<T> l, Comparator<T> c) {
        mergeSort(l, c, 0, l.size() - 1);
    }

    public static <T> void mergeSort(List<T> l, Comparator<T> c, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(l, c, left, mid);
            mergeSort(l, c, mid + 1, right);
            merge(l, c, left, mid, right);
        }
    }

    private static <T> void merge(List<T> l, Comparator<T> c, int left, int mid, int right) {
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

    private SortingClasses() {}
}

