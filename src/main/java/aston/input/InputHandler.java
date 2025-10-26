package aston.input;

import aston.app.entity.Parcel;
import aston.app.validation.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class InputHandler {
    private static List<String> SAMPLE_COUNTRIES = new ArrayList<>(Arrays.asList("USA", "Germany", "Japan"));
    private static final List<String> VALID_TYPES = Arrays.asList("STANDARD", "EXPRESS", "INTERNATIONAL");
    private static final Random RANDOM = new Random();
    private static final List<String> SAMPLE_NAMES = Arrays.asList("John Doe", "Jane Smith", "Alex Brown");

    public static List<Parcel> fillManually(Scanner sc) {
        int count = readParcelCount(sc);
        System.out.println("Ввод данных вручную!");
        List<Parcel> parcels = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            System.out.printf("Посылка %d из %d (или введите 'n' для завершения):%n", i + 1, count);
            String type = readValidType(sc);
            if (type.equalsIgnoreCase("n")) {
                break;
            }

            Map<String, String> data = new HashMap<>();
            data.put("type", type);

            NameField[] nameFields = NameField.values();
            for (NameField element : nameFields) {
                if (element.ifForClass(type)) {
                    String inputString = readPositive(element, sc);
                    data.put(element.getNameField(), inputString);
                }
            }

            try {
                Parcel parcel = Parcel.createFromMap(data);
                parcels.add(parcel);
                System.out.println("Посылка добавлена: " + parcel);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка создания посылки: " + e.getMessage());
            }
        }

        return parcels;
    }

    public static List<Parcel> fillFromFile(String filePath, Scanner sc) {
        int count = readParcelCount(sc);
        List<Parcel> parcels = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String firstLine = reader.readLine();
            if (firstLine != null && firstLine.trim().startsWith("COUNTRIES:")) {
                String[] countries = firstLine.trim().substring(9).split(",");
                SAMPLE_COUNTRIES = new ArrayList<>(Arrays.asList(countries));
                System.out.println("Обновлённый список стран: " + SAMPLE_COUNTRIES);
            }

            for (int i = 0; i < count; i++) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                String[] parts = line.split(",");
                if (parts.length < 4 || !VALID_TYPES.contains(parts[0].trim().toUpperCase(Locale.ROOT))) {
                    System.out.println("Некорректная строка в файле: " + line);
                    continue;
                }

                Map<String, String> data = new HashMap<>();
                data.put("type", parts[0].trim().toUpperCase(Locale.ROOT));
                int index = 1;

                NameField[] nameFields = NameField.values();
                for (NameField element : nameFields) {
                    if (element.ifForClass(data.get("type")) && index < parts.length) {
                        String value = parts[index].trim();
                        if (Validator.check(element, value)) {
                            data.put(element.getNameField(), value);
                        } else {
                            System.out.println("Некорректное значение для " + element.getDescription() + ": " + value);
                            break;
                        }
                        index++;
                    }
                }

                try {
                    Parcel parcel = Parcel.createFromMap(data);
                    if (parcel != null) {
                        parcels.add(parcel);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка создания посылки из строки " + line + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        return parcels;
    }

    public static List<Parcel> fillRandomly(Scanner sc) {
        int count = readParcelCount(sc);
        List<Parcel> parcels = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Map<String, String> data = new HashMap<>();
            String type = VALID_TYPES.get(RANDOM.nextInt(VALID_TYPES.size()));
            data.put("type", type);

            NameField[] nameFields = NameField.values();
            for (NameField element : nameFields) {
                if (element.ifForClass(type)) {
                    String value = generateRandomValue(element);
                    data.put(element.getNameField(), value);
                }
            }

            try {
                Parcel parcel = Parcel.createFromMap(data);
                parcels.add(parcel);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка создания посылки: " + e.getMessage());
            }
        }

        return parcels;
    }

    private static int readParcelCount(Scanner sc) {
        for (int i = 0; i < 5; i++) {
            System.out.print("Введите количество посылок (1–5): ");
            String input = sc.nextLine().trim();
            try {
                int count = Integer.parseInt(input);
                if (count >= 1 && count <= 5) {
                    return count;
                }
                System.out.println("Количество должно быть от 1 до 5.");
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
        throw new IllegalArgumentException("Слишком много некорректных попыток ввода количества.");
    }

    private static String readValidType(Scanner sc) {
        for (int i = 0; i < 5; i++) {
            System.out.print("Введите тип посылки (STANDARD/EXPRESS/INTERNATIONAL) или 'n' для завершения: ");
            String type = sc.nextLine().trim().toUpperCase(Locale.ROOT);
            if (type.equals("N")) {
                return "n";
            }
            if (VALID_TYPES.contains(type)) {
                return type;
            }
            System.out.println("Недопустимый тип посылки. Допустимые типы: " + VALID_TYPES);
        }
        return "n";
    }

    private static String readPositive(NameField element, Scanner sc) {
        for (int i = 0; i < 5; i++) {
            System.out.printf("Заполните поле '%s': ", element.getDescription());
            String inputString = sc.nextLine();
            if (Validator.check(element, inputString)) {
                return inputString;
            }
        }
        throw new IllegalArgumentException("Слишком много некорректных попыток для поля " + element.getDescription());
    }

    private static String generateRandomValue(NameField element) {
        switch (element) {
            case RECIPIENT_NAME:
                return SAMPLE_NAMES.get(RANDOM.nextInt(SAMPLE_NAMES.size()));
            case WEIGHT:
                return String.format(Locale.ROOT, "%.1f", 0.1 + RANDOM.nextDouble() * 99.9);
            case TRACKING_NUMBER:
                return String.valueOf(1 + RANDOM.nextInt(999999));
            case MAX_DIMENSION:
                return String.valueOf(1 + RANDOM.nextInt(100));
            case DELIVERY_DEADLINE:
                LocalDate today = LocalDate.now();
                return today.plusDays(1 + RANDOM.nextInt(365)).toString();
            case DESTINATION_COUNTRY:
                if (SAMPLE_COUNTRIES.isEmpty()) {
                    return "USA"; // Значение по умолчанию на случай пустого списка
                }
                return SAMPLE_COUNTRIES.get(RANDOM.nextInt(SAMPLE_COUNTRIES.size()));
            default:
                return "Unknown";
        }
    }
}