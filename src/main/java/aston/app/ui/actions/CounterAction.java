package aston.app.ui.actions;

import aston.app.core.counter.Counter;
import aston.app.entity.ExpressParcel;
import aston.app.entity.InternationalParcel;
import aston.app.entity.Parcel;
import aston.app.entity.StandardParcel;
import aston.app.ui.ConsoleContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;


public class CounterAction implements MenuAction {

    @Override
    public String key() {
        return "5";
    }

    @Override
    public String title() {
        return "Подсчёт количества посылок по полю";
    }

    @Override
    public void run(ConsoleContext ctx) {
        if (ctx.parcels().isEmpty()) {
            System.out.println("Коллекция пуста. Сначала добавьте посылки.");
            return;
        }

        System.out.print("""
                === Выберите поле для подсчёта ===
                 1) Имя получателя
                 2) Номер отслеживания
                 3) Вес
                 4) Срок доставки
                 5) Страна назначения
                 6) Максимальный габарит
                """);
        System.out.print("Ваш выбор: ");
        String choice = ctx.in().nextLine().trim();

        int threads = Runtime.getRuntime().availableProcessors();

        try {
            switch (choice) {
                case "1": {
                    System.out.print("Введите имя получателя: ");
                    String target = ctx.in().nextLine().trim();
                    List<String> values = ctx.parcels().stream()
                            .map(Parcel::getRecipientName)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    if (values.isEmpty()) {
                        System.out.println("Нет значений для выбранного поля.");
                        return;
                    }
                    Counter.countAndPrintOccurrences(values, target, threads, System.out);
                    break;
                }
                case "2": {
                    System.out.print("Введите trackingNumber: ");
                    int target = Integer.parseInt(ctx.in().nextLine().trim());
                    List<Integer> values = ctx.parcels().stream()
                            .map(Parcel::getTrackingNumber)
                            .collect(Collectors.toList());
                    Counter.countAndPrintOccurrences(values, target, threads, System.out);
                    break;
                }
                case "3": {
                    System.out.print("Введите вес: ");
                    double targetDouble = Double.parseDouble(ctx.in().nextLine().trim());
                    String target = String.format(Locale.ROOT, "%.3f", targetDouble);
                    List<String> values = ctx.parcels().stream()
                            .map(Parcel::getWeight)
                            .map(d -> String.format(Locale.ROOT, "%.3f", d))
                            .collect(Collectors.toList());
                    Counter.countAndPrintOccurrences(values, target, threads, System.out);
                    break;
                }
                case "4": {
                    System.out.print("Введите срок доставки: ");
                    LocalDate target = LocalDate.parse(ctx.in().nextLine().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
                    List<LocalDate> values = ctx.parcels().stream()
                            .map(p -> (p instanceof ExpressParcel e) ? e.getDeliveryDeadline() : null)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    if (values.isEmpty()) {
                        System.out.println("Нет значений для выбранного поля.");
                        return;
                    }
                    Counter.countAndPrintOccurrences(values, target, threads, System.out);
                    break;
                }
                case "5": {
                    System.out.print("Введите страну назначения: ");
                    String target = ctx.in().nextLine().trim();
                    List<String> values = ctx.parcels().stream()
                            .map(p -> (p instanceof InternationalParcel i) ? i.getDestinationCountry() : null)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    if (values.isEmpty()) {
                        System.out.println("Нет значений для выбранного поля.");
                        return;
                    }
                    Counter.countAndPrintOccurrences(values, target, threads, System.out);
                    break;
                }
                case "6": {
                    System.out.print("Введите максимальный габарит: ");
                    int target = Integer.parseInt(ctx.in().nextLine().trim());
                    List<Integer> values = ctx.parcels().stream()
                            .map(p -> (p instanceof StandardParcel s) ? s.getMaxDimension() : null)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    if (values.isEmpty()) {
                        System.out.println("Нет значений для выбранного поля.");
                        return;
                    }
                    Counter.countAndPrintOccurrences(values, target, threads, System.out);
                    break;
                }
                default:
                    System.out.println("Неизвестный пункт меню.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Ошибка ввода числа: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Ошибка: " + ex.getMessage());
        }
    }
}
