package aston.app.ui.actions;

import aston.app.core.comparator.ParcelComparators;
import aston.app.core.search.BinarySearch;
import aston.app.core.sorting.SortingClasses;
import aston.app.entity.ExpressParcel;
import aston.app.entity.InternationalParcel;
import aston.app.entity.Parcel;
import aston.app.entity.StandardParcel;
import aston.app.ui.ConsoleContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchAction implements MenuAction {
    @Override
    public String key() {
        return "4";
    }

    @Override
    public String title() {
        return "Поиск посылки (бинарный поиск)";
    }

    @Override
    public void run(ConsoleContext ctx) {
        System.out.println("=== Поиск посылки ===");
        System.out.println("""
                1) По имени получателя
                2) По весу
                3) По трек-номеру
                4) По максимальному размеру (Standard)
                5) По стране назначения (International)
                6) По сроку доставки (Express, формат YYYY-MM-DD)
                """);
        System.out.print("Ваш выбор: ");
        String choice = ctx.in().nextLine().trim();

        switch (choice) {
            case "1" -> searchByRecipient(ctx);
            case "2" -> searchByWeight(ctx);
            case "3" -> searchByTracking(ctx);
            case "4" -> searchByMaxDimension(ctx);
            case "5" -> searchByCountry(ctx);
            case "6" -> searchByDeadline(ctx);
            default -> System.out.println("Неизвестный пункт.");
        }
    }

    private void searchByRecipient(ConsoleContext ctx) {
        System.out.print("Введите имя получателя: ");
        String name = ctx.in().nextLine().trim();

        List<Parcel> sorted = new ArrayList<>(ctx.parcels());
        SortingClasses.sortList("mergeSort", sorted, ParcelComparators.byRecipientName());

        int index = BinarySearch.searchByRecipient(sorted, name);
        showResult(sorted, index);
    }

    private void searchByWeight(ConsoleContext ctx) {
        System.out.print("Введите вес (например 2.5): ");
        try {
            double weight = Double.parseDouble(ctx.in().nextLine().trim());
            List<Parcel> sorted = new ArrayList<>(ctx.parcels());
            SortingClasses.sortList("mergeSort", sorted, ParcelComparators.byWeight());

            int index = BinarySearch.searchByWeight(sorted, weight);
            showResult(sorted, index);
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат веса.");
        }
    }

    private void searchByTracking(ConsoleContext ctx) {
        System.out.print("Введите трек-номер (целое число): ");
        try {
            int number = Integer.parseInt(ctx.in().nextLine().trim());
            List<Parcel> sorted = new ArrayList<>(ctx.parcels());
            SortingClasses.sortList("mergeSort", sorted, ParcelComparators.byTrackingNumber());

            int index = BinarySearch.searchByTrackingNumber(sorted, number);
            showResult(sorted, index);
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат числа.");
        }
    }

    private void searchByMaxDimension(ConsoleContext ctx) {
        System.out.print("Введите maxDimension (целое число): ");
        try {
            int dimension = Integer.parseInt(ctx.in().nextLine().trim());
            List<StandardParcel> list = ctx.parcels().stream()
                    .filter(p -> p instanceof StandardParcel)
                    .map(p -> (StandardParcel) p)
                    .collect(Collectors.toCollection(ArrayList::new));

            SortingClasses.sortList("mergeSort", list, ParcelComparators.byMaxDimension());

            int index = BinarySearch.searchStandardByMaxDimension(list, dimension);
            showResult(list, index);
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат числа.");
        }
    }

    private void searchByCountry(ConsoleContext ctx) {
        System.out.print("Введите страну назначения: ");
        String country = ctx.in().nextLine().trim();

        List<InternationalParcel> list = ctx.parcels().stream()
                .filter(p -> p instanceof InternationalParcel)
                .map(p -> (InternationalParcel) p)
                .collect(Collectors.toCollection(ArrayList::new));

        SortingClasses.sortList("mergeSort", list, ParcelComparators.byDestinationCountry());

        int index = BinarySearch.searchInternationalByCountry(list, country);
        showResult(list, index);
    }

    private void searchByDeadline(ConsoleContext ctx) {
        System.out.print("Введите срок доставки (YYYY-MM-DD): ");
        try {
            LocalDate date = LocalDate.parse(ctx.in().nextLine().trim());
            List<ExpressParcel> list = ctx.parcels().stream()
                    .filter(p -> p instanceof ExpressParcel)
                    .map(p -> (ExpressParcel) p)
                    .collect(Collectors.toCollection(ArrayList::new));

            SortingClasses.sortList("mergeSort", list, ParcelComparators.byDeliveryDeadline());

            int index = BinarySearch.searchExpressByDeadline(list, date);
            showResult(list, index);
        } catch (Exception e) {
            System.out.println("Неверный формат даты.");
        }
    }

    private <T> void showResult(List<T> list, int index) {
        if (index < 0) {
            System.out.println("Посылка не найдена.");
        } else {
            System.out.println("Найдено: " + list.get(index));
        }
    }
}
