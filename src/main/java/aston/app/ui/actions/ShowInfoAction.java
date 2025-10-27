package aston.app.ui.actions;

import aston.app.entity.Parcel;
import aston.app.ui.ConsoleContext;

public class ShowInfoAction implements MenuAction {
    @Override
    public String key() {
        return "1";
    }

    @Override
    public String title() {
        return "Показать текущие посылки";
    }

    @Override
    public void run(ConsoleContext ctx) {
        if (ctx.parcels().isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }
        System.out.println("Текущие посылки:");
        for (Parcel p : ctx.parcels()) {
            System.out.println(" - " + p);
        }
    }
}
