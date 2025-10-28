package aston.app.ui;

import aston.app.entity.Parcel;
import aston.app.ui.actions.AppInfoAction;
import aston.app.ui.actions.ExitAction;
import aston.app.ui.actions.InputAction;
import aston.app.ui.actions.MenuAction;
import aston.app.ui.actions.SearchAction;
import aston.app.ui.actions.ShowInfoAction;
import aston.app.ui.actions.SortAction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {

    private final Scanner in;
    private final List<Parcel> parcels;
    private final Map<String, MenuAction> actions = new LinkedHashMap<>();
    private boolean running = true;

    public ConsoleUI(Scanner in,
                     List<Parcel> parcels) {
        this.in = in;
        this.parcels = parcels;
        registerActions();
    }

    private void registerActions() {
        addAction(new AppInfoAction());
        addAction(new ShowInfoAction());
        addAction(new InputAction());
        addAction(new SortAction());
        addAction(new SearchAction());
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
            action.run(new ConsoleContext(in, parcels));
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
