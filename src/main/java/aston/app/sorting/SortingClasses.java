package aston.app.sorting;

import java.util.Comparator;
import java.util.List;

public final class SortingClasses {
    public static <T> void sort(List<T> l, Comparator<T> c) {
        int n = l.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (c.compare(l.get(i), l.get(i + 1)) > 0) {
                    T temp = l.get(j);
                    l.set(j, l.get(j + 1));
                    l.set(j + 1, temp);
                }
            }
        }
    }

    private SortingClasses() {
    }
}
