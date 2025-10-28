package aston.app.input.handler;

import aston.app.entity.Parcel;
import aston.app.input.model.NameClass;
import aston.app.input.model.NameField;
import aston.app.input.validator.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class InputHandler {
    public static final List<String> SAMPLE_COUNTRIES =
            new ArrayList<>(Arrays.asList("USA", "Germany", "Japan"));

    public static final List<NameClass> VALID_TYPES =
            Arrays.asList(NameClass.STANDARD, NameClass.EXPRESS, NameClass.INTERNATIONAL);

    private static final Random RANDOM = new Random();
    private static final List<String> SAMPLE_NAMES = Arrays.asList("John Doe", "Jane Smith", "Alex Brown");

    public static List<Parcel> fillManually(Scanner sc) {
        int count = readParcelCount(sc);
        System.out.println("Ввод данных вручную!");
        List<Parcel> parcels = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            System.out.printf("Посылка %d из %d (или введите 'n' для завершения):%n", i + 1, count);
            NameClass type = readValidType(sc);
            if (type == null) {
                break;
            }

            Map<String, String> data = new HashMap<>();
            data.put("type", type.name());

            for (NameField field : NameField.values()) {
                if (field.isApplicableTo(type)) {
                    String value = readFieldValue(sc, field);
                    data.put(field.getNameField(), value);
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
            if (firstLine != null && firstLine.trim().toUpperCase(Locale.ROOT).startsWith("COUNTRIES:")) {
                String raw = firstLine.trim().substring("COUNTRIES:".length());
                List<String> countries = Arrays.stream(raw.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .toList();
                SAMPLE_COUNTRIES.clear();
                SAMPLE_COUNTRIES.addAll(countries);
                System.out.println("Обновлённый список стран: " + SAMPLE_COUNTRIES);
            }

            for (int i = 0; i < count; i++) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }

                String[] parts = line.split(",");
                if (parts.length < 1) {
                    System.out.println("Пустая или некорректная строка: " + line);
                    continue;
                }

                String typeRaw = parts[0].trim();
                NameClass typeEnum = parseType(typeRaw);
                if (typeEnum == null || !VALID_TYPES.contains(typeEnum)) {
                    System.out.println("Некорректный тип в строке: " + line);
                    continue;
                }

                Map<String, String> data = new HashMap<>();
                data.put("type", typeEnum.name());

                int index = 1;
                boolean ok = true;
                for (NameField field : NameField.values()) {
                    if (!field.isApplicableTo(typeEnum)) {
                        continue;
                    }
                    if (index >= parts.length) {
                        System.out.println("Не хватает значений для поля: " + field.getDescription());
                        ok = false;
                        break;
                    }
                    String value = parts[index].trim();
                    if (!Validator.check(field, value)) {
                        System.out.println("Некорректное значение для " + field.getDescription() + ": " + value);
                        ok = false;
                        break;
                    }
                    data.put(field.getNameField(), value);
                    index++;
                }
                if (!ok) {
                    continue;
                }

                try {
                    Parcel parcel = Parcel.createFromMap(data);
                    if (parcel != null) {
                        parcels.add(parcel);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка создания посылки из строки \"" + line + "\": " + e.getMessage());
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
            NameClass type = VALID_TYPES.get(RANDOM.nextInt(VALID_TYPES.size()));

            Map<String, String> data = new HashMap<>();
            data.put("type", type.name());

            for (NameField field : NameField.values()) {
                if (field.isApplicableTo(type)) {
                    String value = generateRandomValue(field);
                    data.put(field.getNameField(), value);
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

    public static int readParcelCount(Scanner sc) {
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

    private static NameClass readValidType(Scanner sc) {
        for (int i = 0; i < 5; i++) {
            System.out.print("Введите тип посылки (STANDARD/EXPRESS/INTERNATIONAL) или 'n' для завершения: ");
            String raw = sc.nextLine().trim();
            if (raw.equalsIgnoreCase("n")) {
                return null;
            }
            NameClass type = parseType(raw);
            if (type != null && VALID_TYPES.contains(type)) {
                return type;
            }
            System.out.println("Недопустимый тип посылки. Допустимые типы: " + VALID_TYPES);
        }
        return null;
    }

    private static NameClass parseType(String raw) {
        try {
            return NameClass.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            return null;
        }
    }

    private static String readFieldValue(Scanner sc, NameField field) {
        for (int i = 0; i < 5; i++) {
            System.out.printf("Заполните поле '%s': ", field.getDescription());
            String value = sc.nextLine();
            if (Validator.check(field, value)) {
                return value.trim();
            }
        }
        throw new IllegalArgumentException("Слишком много некорректных попыток для поля " + field.getDescription());
    }

    private static String generateRandomValue(NameField field) {
        switch (field) {
            case RECIPIENT_NAME:
                return SAMPLE_NAMES.get(RANDOM.nextInt(SAMPLE_NAMES.size()));
            case WEIGHT:
                return String.format(Locale.ROOT, "%.1f", 0.1 + RANDOM.nextDouble() * 99.9);
            case TRACKING_NUMBER:
                return String.valueOf(1 + RANDOM.nextInt(999_999));
            case MAX_DIMENSION:
                return String.valueOf(1 + RANDOM.nextInt(100));
            case DELIVERY_DEADLINE:
                LocalDate today = LocalDate.now();
                return today.plusDays(1 + RANDOM.nextInt(365)).toString();
            case DESTINATION_COUNTRY:
                if (SAMPLE_COUNTRIES.isEmpty()) {
                    return "USA";
                }
                return SAMPLE_COUNTRIES.get(RANDOM.nextInt(SAMPLE_COUNTRIES.size()));
            default:
                return "Unknown";
        }
    }
}
