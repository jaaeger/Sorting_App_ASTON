package aston.app.core.counter;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


public final class Counter {

    private Counter() {
    }

    public static <T> long countAndPrintOccurrences(
            Collection<T> collection,
            T target,
            int threadCount,
            PrintStream out
    ) {
        long count = countOccurrences(collection, target, threadCount);
        int size = (collection == null) ? 0 : collection.size();

        out.printf(
                "Элемент '%s' найден %d раз(а) в коллекции размером %d%n",
                target, count, size
        );
        return count;
    }

    public static <T> long countOccurrences(Collection<T> collection, T target, int threadCount) {

        if (collection == null || collection.isEmpty()) {
            return 0L;
        }

        final List<T> list = new ArrayList<>(collection);
        final int size = list.size();

        final int cores = Runtime.getRuntime().availableProcessors();
        final int threads = (threadCount > 0 ? threadCount : cores);

        final ReentrantLock lock = new ReentrantLock();

        final int[] cursor = {0};

        final long[] totalCount = {0L};

        final int chunkSize = Math.max(1, (size + threads - 1) / threads);

        ExecutorService pool = Executors.newFixedThreadPool(threads);

        for (int t = 0; t < threads; t++) {
            pool.submit(() -> {

                while (true) {
                    int from, to;

                    lock.lock();
                    try {
                        from = cursor[0];
                        if (from >= size) {
                            return;
                        }
                        to = Math.min(from + chunkSize, size);
                        cursor[0] = to;
                    } finally {
                        lock.unlock();
                    }

                    long localCount = 0;
                    for (int i = from; i < to; i++) {
                        if (Objects.equals(list.get(i), target)) {
                            localCount++;
                        }
                    }
                    lock.lock();
                    try {
                        totalCount[0] += localCount;
                    } finally {
                        lock.unlock();
                    }
                }
            });
        }

        pool.shutdown();
        try {
            if (!pool.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("Превышено время ожидания — завершаем принудительно.");
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Главный поток прерван во время ожидания завершения.");
        }

        return totalCount[0];
    }
}
