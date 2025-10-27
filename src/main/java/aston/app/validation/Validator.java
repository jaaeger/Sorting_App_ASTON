package aston.app.validation;

import aston.app.input.InputHandler;
import aston.app.input.NameField;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class Validator {
    private static final Map<NameField, Function<String, Boolean>> validators = new HashMap<>();

    static {
        validators.put(NameField.RECIPIENT_NAME, Validator::validateRecipientName);
        validators.put(NameField.WEIGHT, Validator::validateWeight);
        validators.put(NameField.TRACKING_NUMBER, Validator::validateTrackingNumber);
        validators.put(NameField.MAX_DIMENSION, Validator::validateMaxDimension);
        validators.put(NameField.DELIVERY_DEADLINE, Validator::validateDeliveryDeadline);
        validators.put(NameField.DESTINATION_COUNTRY, Validator::validateDestinationCountry);
    }

    public static boolean check(NameField element, String inputString) {
        inputString = inputString.trim();

        if (inputString.isEmpty()) {
            System.out.println("Поле не может быть пустым, попробуйте снова.");
            return false;
        }

        Scanner sc = new Scanner(inputString).useLocale(Locale.ROOT);

        Function<String, Boolean> validator = validators.getOrDefault(element, input -> true);
        return validator.apply(inputString);
    }

    private static boolean validateRecipientName(String inputString) {
        return true;
    }

    private static boolean validateWeight(String inputString) {
        Scanner sc = new Scanner(inputString).useLocale(Locale.ROOT);
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
    }

    private static boolean validateTrackingNumber(String inputString) {
        Scanner sc = new Scanner(inputString).useLocale(Locale.ROOT);
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
    }

    private static boolean validateMaxDimension(String inputString) {
        Scanner sc = new Scanner(inputString).useLocale(Locale.ROOT);
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
    }

    private static boolean validateDeliveryDeadline(String inputString) {
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
    }

    private static boolean validateDestinationCountry(String inputString) {
        if (InputHandler.SAMPLE_COUNTRIES.contains(inputString)) {
            return true;
        } else {
            System.out.println("Недопустимая страна. Допустимые страны: " + InputHandler.SAMPLE_COUNTRIES);
            return false;
        }
    }
}