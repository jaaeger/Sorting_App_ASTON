package aston.app.ui.actions;

import aston.app.entity.Parcel;
import aston.app.input.handler.InputHandler;
import aston.app.ui.ConsoleContext;

import java.util.List;

public class InputAction implements MenuAction {
    @Override
    public String key() {
        return "2";
    }

    @Override
    public String title() {
        return "Заполнить посылки (ручной/файл/рандом)";
    }

    @Override
    public void run(ConsoleContext ctx) {
        System.out.println("""
                == Заполнение ==
                1) Ввести вручную
                2) Прочитать из файла
                3) Сгенерировать случайно
                """);
        System.out.print("Ваш выбор: ");
        String choice = ctx.in().nextLine().trim();

        try {
            List<Parcel> added = switch (choice) {
                case "1" -> InputHandler.fillManually(ctx.in());
                case "2" -> {
                    System.out.print("Путь к файлу: ");
                    String path = ctx.in().nextLine().trim();
                    yield InputHandler.fillFromFile(path, ctx.in());
                }
                case "3" -> InputHandler.fillRandomly(ctx.in());
                default -> {
                    System.out.println("Неизвестный пункт.");
                    yield java.util.Collections.emptyList();
                }
            };
            ctx.parcels().addAll(added);
            System.out.println("Добавлено посылок: " + added.size());
        } catch (Exception e) {
            System.out.println("Ошибка заполнения: " + e.getMessage());
        }
    }
}
