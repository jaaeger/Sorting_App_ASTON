package aston.app.ui.actions;

import aston.app.core.comparator.ParcelComparators;
import aston.app.core.sorting.SortingClasses;
import aston.app.entity.Parcel;
import aston.app.entity.StandardParcel;
import aston.app.input.model.NameField;
import aston.app.ui.ConsoleContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class SortAction implements MenuAction {
    @Override
    public String key() {
        return "3";
    }

    @Override
    public String title() {
        return "Сортировать (выбор алгоритма)";
    }

    @Override
    public void run(ConsoleContext ctx) {
        System.out.println("Выберите алгоритм сортировки:");
        System.out.println(" 1) quickSort");
        System.out.println(" 2) mergeSort");
        System.out.print("Ваш выбор: ");
        String algoChoice = ctx.in().nextLine().trim();

        String algo;
        switch (algoChoice) {
            case "1" -> algo = "quickSort";
            case "2" -> algo = "mergeSort";
            default -> {
                System.out.println("Неизвестный выбор алгоритма: " + algoChoice);
                return;
            }
        }

        System.out.println("\nРежим сортировки:");
        System.out.println(" 1) Обычная (все элементы)");
        System.out.println(" 2) Только элементы с ЧЁТНЫМ значением числового поля (НЕЧЁТНЫЕ остаются на местах)");
        System.out.print("Ваш выбор: ");
        String modeChoice = ctx.in().nextLine().trim();
        boolean evenOnly = switch (modeChoice) {
            case "1" -> false;
            case "2" -> true;
            default -> {
                System.out.println("Неизвестный режим: " + modeChoice);
                yield false;
            }
        };
        if (!modeChoice.equals("1") && !modeChoice.equals("2")) {
            return;
        }

        NameField[] all = NameField.values();
        List<NameField> options = new ArrayList<>();
        for (NameField f : all) {
            if (!evenOnly || f == NameField.TRACKING_NUMBER || f == NameField.MAX_DIMENSION) {
                options.add(f);
            }
        }
        if (options.isEmpty()) {
            System.out.println("Нет полей, поддерживающих режим \"только чётные\".");
            return;
        }

        System.out.println("\nДоступные поля для сортировки:");
        for (int i = 0; i < options.size(); i++) {
            NameField f = options.get(i);
            System.out.printf(" %d) %-18s — %s (%s)%n",
                    i + 1, f.name(), f.getDescription(), f.getNameClass().name());
        }
        System.out.print("Ваш выбор: ");
        String fieldChoice = ctx.in().nextLine().trim();

        int idx;
        try {
            idx = Integer.parseInt(fieldChoice) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Нужно ввести номер пункта.");
            return;
        }
        if (idx < 0 || idx >= options.size()) {
            System.out.println("Номер вне диапазона.");
            return;
        }

        NameField field = options.get(idx);

        Comparator<? super Parcel> cmp;
        try {
            cmp = ParcelComparators.byField(field);
        } catch (IllegalArgumentException ex) {
            System.out.println("Для поля " + field.getNameField() + " компаратор не найден.");
            return;
        }

        try {
            if (!evenOnly) {
                SortingClasses.sortList(algo, ctx.parcels(), cmp);
            } else {
                Function<Parcel, Integer> key = switch (field) {
                    case TRACKING_NUMBER -> Parcel::getTrackingNumber;
                    case MAX_DIMENSION -> p -> {
                        if (p instanceof StandardParcel sp) {
                            return sp.getMaxDimension();
                        }
                        return null;
                    };
                    default -> null;
                };
                if (key == null) {
                    System.out.println("Режим \"только чётные\" не поддерживается для поля: " + field.getNameField());
                    return;
                }
                SortingClasses.sortByNumericField(algo, ctx.parcels(), cmp, key);
            }

            System.out.println("\nОтсортировано алгоритмом " + algo +
                    (evenOnly ? " (только чётные по выбранному полю)" : "") +
                    " по полю " + field.getNameField() +
                    " класса " + field.getNameClass() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка сортировки: " + e.getMessage());
        }
    }
}
