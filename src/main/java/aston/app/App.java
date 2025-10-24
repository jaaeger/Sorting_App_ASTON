package aston.app;

import aston.app.entity.Parcel;
import aston.app.input.NameField;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println(fillManually(new Scanner(System.in)).toString());
    }

    private static Parcel fillManually(Scanner sc) { // Parcel -> List<Parcel> и цикл внутри сделать
        System.out.println("Ввод данных вручную!");

        System.out.print("Введите тип класса: ");

        String type = sc.nextLine().toUpperCase(Locale.ROOT);

        //Добавить для типа валидацию

        Map<String, String> data = new HashMap<>();
        data.put("type", type);

        NameField[] nameFields = NameField.values();

        for (NameField element : nameFields) {
            if (element.ifForClass(type)) {
                String inputString = readPositive(element, sc); //Валидация

                data.put(element.getNameField(), inputString);
            }
        }

        Parcel parcel = Parcel.createFromMap(data);

        System.out.println(parcel);

        return parcel;
    }

    private static String readPositive(NameField element, Scanner sc) {
        while (true) {
            System.out.printf("Заполните поле '%s': ", element.getDescription());
            String inputString = sc.nextLine();
            if (check(element, inputString)) {
                return inputString;
            }
        }
    }

    private static boolean check(NameField element, String inputString) {
        inputString = inputString.trim();

        if (inputString.isEmpty()) {
            System.out.println("Field cannot be empty, try again.");
            return false;
        }

        Scanner sc = new Scanner(inputString);

        // Нужно написать почему вышла ошибка
        switch (element) {
            case WEIGHT, MAX_DIMENSION:
                return sc.hasNextDouble() && sc.nextDouble() > 0;
            case TRACKING_NUMBER:
                return sc.hasNextInt() && sc.nextInt() > 0;
            case DELIVERY_DEADLINE:
                try {
                    LocalDate localDate = LocalDate.parse(inputString.trim());
                    return localDate.isAfter(LocalDate.now());
                } catch (DateTimeParseException e) {
                    return false;
                }
            default: return true;
        }
    }

}
