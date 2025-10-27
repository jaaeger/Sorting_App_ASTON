package aston.app.InputTest;

import aston.app.entity.Parcel;
import aston.app.input.InputHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {

    @Test
    void whenFillManuallyValidInput_thenParcelCreated() {
        String input = "1\nSTANDARD\nJohn Doe\n10.0\n12345\n20\n2025-11-01\nUSA\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        List<Parcel> parcels = InputHandler.fillManually(new Scanner(System.in));

        assertEquals(1, parcels.size());
    }

    @Test
    void whenReadParcelCountValid_thenReturnNumber() {
        System.setIn(new ByteArrayInputStream("3\n".getBytes()));
        assertEquals(3, InputHandler.readParcelCount(new Scanner(System.in)));
    }

    @Test
    void whenFillFromFileValid_thenCreated(
            @TempDir Path tempDir
    ) throws IOException {
        Path file = tempDir.resolve("parcels.txt");
        String content = "COUNTRIES:USA\nSTANDARD,John,5.5,10,20\n";
        java.nio.file.Files.writeString(file, content);

        List<Parcel> parcels = InputHandler.fillFromFile(
                file.toString(),
                new Scanner("1\n")
        );

        assertEquals(1, parcels.size());
    }

    @Test
    void whenFillRandomlyOneParcel_thenReturnOne() {
        List<Parcel> parcels = InputHandler.fillRandomly(new Scanner("1\n"));
        assertEquals(1, parcels.size());
    }
}
