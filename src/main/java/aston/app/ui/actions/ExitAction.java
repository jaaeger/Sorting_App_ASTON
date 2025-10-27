package aston.app.ui.actions;

import aston.app.ui.ConsoleContext;

public class ExitAction implements MenuAction {
    private final Runnable onExit;

    public ExitAction(Runnable onExit) {
        this.onExit = onExit;
    }

    @Override
    public String key() {
        return "q";
    }

    @Override
    public String title() {
        return "Выход";
    }

    @Override
    public void run(ConsoleContext ctx) {
        System.out.println("Выходим...");
        onExit.run();
    }
}
