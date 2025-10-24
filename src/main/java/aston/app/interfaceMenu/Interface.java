package aston.app.interfaceMenu;

import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Interface {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    displayInfo();
                    break;
                case 2:
                    try {
                        int[] data = inputData();
                        System.out.println("Исходный массив: " + Arrays.toString(data));
                    } catch (IOException e) {
                        System.err.println("Ошибка ввода-вывода: " + e.getMessage());
                    }
                    break;
                case 3:
                    sortData();
                    break;
                case 4:
                    findElement();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Некорректный выбор");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\nМеню приложения:");
        System.out.println("1. Информация о программе");
        System.out.println("2. Ввод данных для сортировки");
        System.out.println("3. Сортировка");
        System.out.println("4. Поиск элемента");
        System.out.println("5. Выход\n");
    }

    private static void displayInfo() {
        System.out.println("Краткое описание программы:\nПозже добавлю");
        System.out.println("Информация о вводимых данных:\nВы можете ввести данные вручную, загрузить из файла или автоматически создать массив случайных чисел.");
    }

    private static int[] inputData() throws IOException {
        System.out.println("Выберите способ ввода данных:");
        System.out.println("1. Из файла");
        System.out.println("2. Вручную");
        System.out.println("3. Рандом");
        int choice = scanner.nextInt();

        System.out.print("Введите длину массива: ");
        int length = scanner.nextInt();
        int[] data = new int[length];



        switch (choice) {
            case 1:
                readFromFile(data);
                break;
            case 2:
                manualInput(data);
                break;
            case 3:
                randomInput(data);
                break;
            default:
                System.out.println("Неправильный выбор метода ввода данных");
                return null;
        }
        return data;
    }

    private static void readFromFile(int[] data) throws IOException {
        System.out.print("Введите путь к файлу: ");
        scanner.nextLine(); // сброс буфера после nextInt()
        String filePath = scanner.nextLine();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        int index = 0;
        while ((line = br.readLine()) != null && index < data.length) {
            data[index++] = Integer.parseInt(line.trim());
        }
        br.close();
    }

    private static void manualInput(int[] data) {
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < data.length; i++) {
            data[i] = scanner.nextInt();
        }
    }

    private static void randomInput(int[] data) {
        Random rand = new Random();
        for (int i = 0; i < data.length; i++) {
            data[i] = rand.nextInt(100); // Не знаю, какое максимальное число надо по условию
        }
    }

    private static void sortData() {
        System.out.println("Нужно заранее вызвать inputData(), чтобы задать массив для сортировки.");
    }

    private static void findElement() {
        System.out.println("Нужно заранее вызвать inputData(), чтобы задать массив для поиска.");
    }
}
