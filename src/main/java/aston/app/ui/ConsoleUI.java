package aston.app.ui;

import aston.app.entity.Parcel;
import aston.app.input.NameField;
import aston.app.ui.actions.AppInfoAction;
import aston.app.ui.actions.ExitAction;
import aston.app.ui.actions.MenuAction;
import aston.app.ui.actions.SearchAction;
import aston.app.ui.actions.ShowInfoAction;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {

    private final Scanner in;
    private final List<Parcel> parcels;
    private final Map<NameField, Comparator<? super Parcel>> comparators;
    private final Map<String, MenuAction> actions = new LinkedHashMap<>();
    private boolean running = true;

    public ConsoleUI(Scanner in,
                     List<Parcel> parcels,
                     Map<NameField, Comparator<? super Parcel>> comparators) {
        this.in = in;
        this.parcels = parcels;
        this.comparators = new EnumMap<>(NameField.class);
        this.comparators.putAll(comparators);
        registerActions();
    }

    private void registerActions() {
        ConsoleContext ctx = new ConsoleContext(in, parcels, comparators);

        addAction(new AppInfoAction());
        addAction(new ShowInfoAction());
        // addAction(new InputAction());
        // addAction(new SortAction());
        // addAction(new SearchAction());
        addAction(new ExitAction(() -> running = false));
    }

    public void addAction(MenuAction action) {
        actions.put(action.key(), action);
    }

    public void run() {
        while (running) {
            printMenu();
            String choice = in.nextLine().trim();
            MenuAction action = actions.get(choice);
            if (action == null) {
                System.out.println("Неизвестная команда: " + choice);
                continue;
            }
            action.run(new ConsoleContext(in, parcels, comparators));
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("=== Меню ===");
        for (MenuAction a : actions.values()) {
            System.out.printf("%s) %s%n", a.key(), a.title());
        }
        System.out.print("Ваш выбор: ");
    }
}
