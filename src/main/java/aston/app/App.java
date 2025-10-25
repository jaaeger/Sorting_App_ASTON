package aston.app;

import aston.app.entity.Parcel;
import aston.app.input.NameField;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class CustomArrayList<T> implements Iterable<T> {
    private Object[] array;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public CustomArrayList() {
        array = new Object[INITIAL_CAPACITY];
        size = 0;
    }

    public void add(T element) {
        if (size == array.length) {
            array = Arrays.copyOf(array, array.length * 2);
        }
        array[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) array[index];
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) array[currentIndex++];
            }
        };
    }

    public Stream<T> stream() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator(), Spliterator.ORDERED), false);
    }
}

public class App {
    private static final List<String> VALID_TYPES = Arrays.asList("STANDARD", "EXPRESS");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Parcel> parcels = fillManually(sc);
        System.out.println("Введённые посылки: " + parcels);
        sc.close();
    }

    private static List<Parcel> fillManually(Scanner sc) {
        System.out.println("Ввод данных вручную!");
        CustomArrayList<Parcel> parcels = new CustomArrayList<>();

        while (true) {
            String type = readValidType(sc);

            Map<String, String> data = new HashMap<>();
            data.put("type", type);

            NameField[] nameFields = NameField.values();

            for (NameField element : nameFields) {
                if (element.ifForClass(type)) {
                    String inputString = readPositive(element, sc);
                    data.put(element.getNameField(), inputString);
                }
            }

            Parcel parcel = Parcel.createFromMap(data);
            parcels.add(parcel);
            System.out.println("Посылка добавлена: " + parcel);

            System.out.print("Добавить ещё посылку? (y/n): ");
            String continueInput = sc.nextLine().trim().toLowerCase(Locale.ROOT);
            if (!continueInput.equals("y")) {
                break;
            }
        }

        return parcels.stream().collect(Collectors.toCollection(CustomArrayList::new));
    }

    private static String readValidType(Scanner sc) {
        while (true) {
            System.out.print("Введите тип посылки (STANDARD/EXPRESS): ");
            String type = sc.nextLine().trim().toUpperCase(Locale.ROOT);
            if (VALID_TYPES.contains(type)) {
                return type;
            }
            System.out.println("Недопустимый тип посылки. Допустимые типы: " + VALID_TYPES);
        }
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
            System.out.println("Поле не может быть пустым, попробуйте снова.");
            return false;
        }

        Scanner sc = new Scanner(inputString);

        switch (element) {
            case WEIGHT:
                try {
                    if (sc.hasNextDouble()) {
                        double weight = sc.nextDouble();
                        if (weight > 0) {
                            return true;
                        } else {
                            System.out.println("Вес должен быть положительным числом.");
                        }
                    } else {
                        System.out.println("Вес должен быть числом (например, 12.5).");
                    }
                } catch (Exception e) {
                    System.out.println("Некорректный формат веса.");
                }
                return false;

            case MAX_DIMENSION:
                try {
                    if (sc.hasNextDouble()) {
                        double dimension = sc.nextDouble();
                        if (dimension > 0) {
                            return true;
                        } else {
                            System.out.println("Максимальный размер должен быть положительным числом.");
                        }
                    } else {
                        System.out.println("Максимальный размер должен быть числом (например, 30.0).");
                    }
                } catch (Exception e) {
                    System.out.println("Некорректный формат размера.");
                }
                return false;

            case TRACKING_NUMBER:
                try {
                    if (sc.hasNextInt()) {
                        int trackingNumber = sc.nextInt();
                        if (trackingNumber > 0) {
                            return true;
                        } else {
                            System.out.println("Номер отслеживания должен быть положительным целым числом.");
                        }
                    } else {
                        System.out.println("Номер отслеживания должен быть целым числом (например, 12345).");
                    }
                } catch (Exception e) {
                    System.out.println("Некорректный формат номера отслеживания.");
                }
                return false;

            case DELIVERY_DEADLINE:
                try {
                    LocalDate localDate = LocalDate.parse(inputString.trim());
                    if (localDate.isAfter(LocalDate.now())) {
                        return true;
                    } else {
                        System.out.println("Дата доставки должна быть в будущем (после " + LocalDate.now() + ").");
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Дата доставки должна быть в формате ГГГГ-ММ-ДД (например, 2025-12-31).");
                    return false;
                }

            default:
                return true;
        }
    }
}