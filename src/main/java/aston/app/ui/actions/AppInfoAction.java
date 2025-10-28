package aston.app.ui.actions;

import aston.app.input.model.NameField;
import aston.app.ui.ConsoleContext;

import java.util.Comparator;
import java.util.Map;

public class AppInfoAction implements MenuAction {
    @Override
    public String key() {
        return "0";
    }

    @Override
    public String title() {
        return "О приложении";
    }

    @Override
    public void run(ConsoleContext ctx) {
        System.out.println("=== Parcel Sorting App ===");

        System.out.println("Текущих посылок: " + ctx.parcels().size());

        System.out.println("\nДоступные поля:");
        for (NameField f : NameField.values()) {
            System.out.printf(" - %-18s (%s) — %s%n",
                    f.name(),
                    f.getNameClass().name(),
                    f.getDescription());
        }

        System.out.println("\nСортировка доступна по полям:");
        for (Map.Entry<NameField, Comparator<? super aston.app.entity.Parcel>> e : ctx.comparators().entrySet()) {
            NameField f = e.getKey();
            System.out.printf(" - %s (%s)%n", f.name(), f.getNameClass().name());
        }

        System.out.println("\nСовместимость:");
        System.out.println(" - PARCEL — общее поле для всех типов посылок");
        System.out.println(" - EXPRESS — только для экспресс-отправлений");
        System.out.println(" - INTERNATIONAL — только для международных");
        System.out.println(" - STANDARD — только для стандартных");
    }
}
