package aston.app.output;

import aston.app.ui.ConsoleContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

public final class WriterFile {
    public static void writeFile(ConsoleContext ctx, String message, String content) {
        System.out.print(message);
        String save = ctx.in().nextLine().trim();
        if ("y".equalsIgnoreCase(save)) {
            System.out.print("Название отчета: ");
            String name = ctx.in().nextLine().trim();
            try {
                Path outputDir = Path.of("output");
                Files.createDirectories(outputDir);
                Path filePath = outputDir.resolve(name + ".txt");
                Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                System.out.println("Сохранено в " + filePath.toAbsolutePath());
            } catch (Exception e) {
                System.out.println("Ошибка сохранения: " + e.getMessage());
            }
        }
    }

    private WriterFile() {}
}
