package aston.app.interfaceMenu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


interface InputStrategy {
    int[] execute(int[] array) throws IOException;
}

class ReadFromFile implements InputStrategy {
    @Override
    public int[] execute(int[] array) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к файлу: ");
        String filePath = scanner.nextLine();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int index = 0;
        while ((line = reader.readLine()) != null && index < array.length) {
            array[index++] = Integer.parseInt(line.trim());
        }
        reader.close();
        return array;
    }
}


class ManualInput implements InputStrategy {
    @Override
    public int[] execute(int[] array) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < array.length; i++) {
            array[i] = scanner.nextInt();
        }
        return array;
    }
}


class RandomInput implements InputStrategy {
    @Override
    public int[] execute(int[] array) {
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(100); // Генерация случайных чисел от 0 до 99
        }
        return array;
    }
}


class InputStrategyContext {
    private final InputStrategy strategy;

    public InputStrategyContext(InputStrategy strategy) {
        this.strategy = strategy;
    }

    public int[] execute(int[] array) throws IOException {
        return strategy.execute(array);
    }
}

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
                        if (data != null)
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

        InputStrategy selectedStrategy;
        switch (choice) {
            case 1:
                selectedStrategy = new ReadFromFile();
                break;
            case 2:
                selectedStrategy = new ManualInput();
                break;
            case 3:
                selectedStrategy = new RandomInput();
                break;
            default:
                System.out.println("Неправильный выбор метода ввода данных");
                return null;
        }

        InputStrategyContext context = new InputStrategyContext(selectedStrategy);
        return context.execute(data);
    }

    private static void sortData() {
        System.out.println("Нужно заранее вызвать inputData(), чтобы задать массив для сортировки.");
    }

    private static void findElement() {
        System.out.println("Нужно заранее вызвать inputData(), чтобы задать массив для поиска.");
    }
}
