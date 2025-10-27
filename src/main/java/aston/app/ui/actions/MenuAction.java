package aston.app.ui.actions;

import aston.app.ui.ConsoleContext;

public interface MenuAction {
    String key();

    String title();

    void run(ConsoleContext ctx);
}
