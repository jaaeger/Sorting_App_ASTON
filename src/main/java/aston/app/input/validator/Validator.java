package aston.app.input.validator;

import aston.app.input.handler.InputHandler;
import aston.app.input.model.NameField;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Validator {
    private static final Map<NameField, Function<String, Boolean>> validators;

    static {
        Map<NameField, Function<String, Boolean>> m = new HashMap<>();
        m.put(NameField.RECIPIENT_NAME, Validator::validateRecipientName);
        m.put(NameField.WEIGHT, Validator::validateWeight);
        m.put(NameField.TRACKING_NUMBER, Validator::validateTrackingNumber);
        m.put(NameField.MAX_DIMENSION, Validator::validateMaxDimension);
        m.put(NameField.DELIVERY_DEADLINE, Validator::validateDeliveryDeadline);
        m.put(NameField.DESTINATION_COUNTRY, Validator::validateDestinationCountry);
        validators = java.util.Collections.unmodifiableMap(m);
    }


    public static boolean check(NameField element, String inputString) {
        if (inputString == null) {
            System.out.println("Поле не может быть null.");
            return false;
        }
        String value = inputString.trim();
        if (value.isEmpty()) {
            System.out.println("Поле не может быть пустым, попробуйте снова.");
            return false;
        }
        Function<String, Boolean> validator = validators.get(element);
        if (validator == null) {
            return true;
        }
        return validator.apply(value);
    }


    private static boolean validateRecipientName(String inputString) {
        String s = inputString.trim();
        if (s.length() < 2 || s.length() > 40) {
            System.out.println("Имя должно быть длиной от 2 до 40 символов.");
            return false;
        }

        if (!s.matches("[\\p{L}\\s\\-]+")) {
            System.out.println("Имя может содержать только буквы, пробелы и дефисы.");
            return false;
        }
        return true;
    }


    private static boolean validateWeight(String inputString) {
        try {
            double weight = Double.parseDouble(inputString.trim().replace(',', '.'));
            if (weight > 0) {
                return true;
            }
            System.out.println("Вес должен быть положительным числом.");
            return false;
        } catch (Exception e) {
            System.out.println("Вес должен быть числом (например, 12.5).");
            return false;
        }
    }


    private static boolean validateTrackingNumber(String inputString) {
        try {
            int trackingNumber = Integer.parseInt(inputString.trim());
            if (trackingNumber > 0) {
                return true;
            }
            System.out.println("Номер отслеживания должен быть положительным целым числом.");
            return false;
        } catch (Exception e) {
            System.out.println("Номер отслеживания должен быть целым числом (например, 12345).");
            return false;
        }
    }


    private static boolean validateMaxDimension(String inputString) {
        try {
            int dimension = Integer.parseInt(inputString.trim());
            if (dimension > 0) {
                return true;
            }
            System.out.println("Максимальный размер должен быть положительным целым числом.");
            return false;
        } catch (Exception e) {
            System.out.println("Максимальный размер должен быть целым числом (например, 30).");
            return false;
        }
    }


    private static boolean validateDeliveryDeadline(String inputString) {
        try {
            LocalDate localDate = LocalDate.parse(inputString.trim());
            if (localDate.isAfter(LocalDate.now())) {
                return true;
            }
            System.out.println("Дата доставки должна быть в будущем (после " + LocalDate.now() + ").");
            return false;
        } catch (DateTimeParseException e) {
            System.out.println("Дата должна быть в формате YYYY-MM-DD (например, 2025-12-31).");
            return false;
        }
    }


    private static boolean validateDestinationCountry(String inputString) {
        String value = inputString.trim();
        boolean ok = InputHandler.SAMPLE_COUNTRIES.stream()
                .anyMatch(c -> c.equalsIgnoreCase(value));
        if (ok) {
            return true;
        }
        System.out.println("Недопустимая страна. Допустимые страны: " + InputHandler.SAMPLE_COUNTRIES);
        return false;
    }
}