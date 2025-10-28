package aston.app.core.counter;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CounterTest {

    @Test
    void when_collectionContainsTarget_then_countIsCorrect() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i % 5);
        }
        long count = Counter.countOccurrences(list, 4, 4);
        assertEquals(20, count);
    }

    @Test
    void when_emptyCollection_then_zero() {
        long count = Counter.countOccurrences(Collections.emptyList(), "x", 2);
        assertEquals(0, count);
    }

    @Test
    void when_nullCollection_then_zero() {
        long count = Counter.countOccurrences(null, "x", 3);
        assertEquals(0, count);
    }

    @Test
    void when_countAndPrintOccurrences_then_outputContainsThreadInfo() {
        List<String> list = List.of("x", "y", "x", "z", "x");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);

        long count = Counter.countAndPrintOccurrences(list, "x", 3, out);

        assertEquals(3, count);
        String printed = baos.toString();
        assertTrue(printed.contains("Элемент 'x' найден 3"),
                "Ожидаем строку с результатом, а получили:\n" + printed);
    }
}