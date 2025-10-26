package aston.validation;

import aston.app.input.NameField;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class Validator {
    public static boolean check(NameField element, String inputString) {
        inputString = inputString.trim();

        if (inputString.isEmpty()) {
            System.out.println("Поле не может быть пустым, попробуйте снова.");
            return false;
        }

        Scanner sc = new Scanner(inputString).useLocale(Locale.ROOT);

        switch (element) {
            case RECIPIENT_NAME:
                return true;
            case WEIGHT:
                try {
                    if (sc.hasNextDouble()) {
                        double weight = sc.nextDouble();
                        if (weight > 0) {
                            return true;
                        } else {
                            System.out.println("Вес должен быть положительным числом.");
                            return false;
                        }
                    } else {
                        System.out.println("Вес должен быть числом (например, 12.5).");
                        return false;
                    }
                } catch (Exception e) {
                    System.out.println("Некорректный формат веса.");
                    return false;
                }
            case TRACKING_NUMBER:
                try {
                    if (sc.hasNextInt()) {
                        int trackingNumber = sc.nextInt();
                        if (trackingNumber > 0) {
                            return true;
                        } else {
                            System.out.println("Номер отслеживания должен быть положительным целым числом.");
                            return false;
                        }
                    } else {
                        System.out.println("Номер отслеживания должен быть целым числом (например, 12345).");
                        return false;
                    }
                } catch (Exception e) {
                    System.out.println("Некорректный формат номера отслеживания.");
                    return false;
                }
            case MAX_DIMENSION:
                try {
                    if (sc.hasNextInt()) {
                        int dimension = sc.nextInt();
                        if (dimension > 0) {
                            return true;
                        } else {
                            System.out.println("Максимальный размер должен быть положительным целым числом.");
                            return false;
                        }
                    } else {
                        System.out.println("Максимальный размер должен быть целым числом (например, 30).");
                        return false;
                    }
                } catch (Exception e) {
                    System.out.println("Некорректный формат размера.");
                    return false;
                }
            case DELIVERY_DEADLINE:
                try {
                    LocalDate localDate = LocalDate.parse(inputString.trim());
                    if (localDate.isAfter(LocalDate.now())) {
                        return true;
                    } else {
                        System.out.println("Дата доставки должна быть в будущем (после " + LocalDate.now() + ").");
                        return false;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Дата доставки должна быть в формате ГГГГ-ММ-ДД (например, 2025-12-31).");
                    return false;
                }
            case DESTINATION_COUNTRY:
                // Предполагаем, что список SAMPLE_COUNTRIES доступен через InputHandler
                if (InputHandler.SAMPLE_COUNTRIES.contains(inputString)) {
                    return true;
                } else {
                    System.out.println("Недопустимая страна. Допустимые страны: " + InputHandler.SAMPLE_COUNTRIES);
                    return false;
                }
            default:
                return true;
        }
    }
}