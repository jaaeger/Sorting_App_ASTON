package aston.app;

import aston.app.entity.Parcel;
import aston.app.input.NameClass;
import aston.app.input.NameField;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Map<String, String> data = new HashMap<>();

        Scanner sc = new Scanner(System.in);

        System.out.print("Введите тип класса: ");

        String type = sc.nextLine().toUpperCase(Locale.ROOT);
        data.put("type", type);

        NameField[] nameFields = NameField.values();

        for (NameField element : nameFields) {
            if (element.ifForClass(type)) {
                System.out.printf("Заполните поле '%s': ", element.getDescription());
                String inputString = sc.nextLine();
                data.put(element.getNameField(), inputString);
            }
        }

        System.out.println(Parcel.createFromMap(data));
    }
}
