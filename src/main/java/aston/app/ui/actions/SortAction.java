package aston.app.ui.actions;

import aston.app.core.sorting.SortingClasses;
import aston.app.entity.Parcel;
import aston.app.input.model.NameField;
import aston.app.ui.ConsoleContext;

import java.util.Comparator;
import java.util.Map;

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
        System.out.println("Алгоритмы: quickSort | mergeSort");
        System.out.print("Введите алгоритм: ");
        String algo = ctx.in().nextLine().trim();
        if (!algo.equalsIgnoreCase("quickSort") && !algo.equalsIgnoreCase("mergeSort")) {
            System.out.println("Неизвестный алгоритм: " + algo);
            return;
        }

        algo = algo.equalsIgnoreCase("quickSort") ? "quickSort" : "mergeSort";


        System.out.println("Доступные поля для сортировки:");
        for (NameField f : ctx.comparators().keySet()) {
            System.out.println(" - " + f.name() + " (" + f.getDescription() + ")");
        }
        System.out.print("Введите поле (точно как в перечислении): ");
        String fieldRaw = ctx.in().nextLine().trim().toUpperCase();

        NameField field;
        try {
            field = NameField.valueOf(fieldRaw);
        } catch (IllegalArgumentException ex) {
            System.out.println("Неизвестное поле: " + fieldRaw);
            return;
        }

        Map<NameField, Comparator<? super Parcel>> m = ctx.comparators();
        Comparator<? super Parcel> cmp = m.get(field);
        if (cmp == null) {
            System.out.println("Для поля " + field.getNameField() + " компаратор не зарегистрирован.");
            return;
        }

        try {
            SortingClasses.sortList(algo, ctx.parcels(), cmp);
            System.out.println("Отсортировано алгоритмом " + algo + " по полю " + field.getNameField() +
                    " класса " + field.getNameClass() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка сортировки: " + e.getMessage());
        }
    }
}
