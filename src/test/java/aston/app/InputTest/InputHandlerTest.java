package aston.app.InputTest;

import aston.app.entity.Parcel;
import aston.app.input.NameField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class InputHandlerTest {

    private Scanner scanner;

    @BeforeEach
    void setUp() {
        scanner = new Scanner(System.in);
    }

    @Test
    void whenFillManuallyWithOneValidParcel_thenOneParcelCreated() {
        String input = "1\nSTANDARD\nJohn Doe\n10.0\n12345\n20\n2025-11-01\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        scanner = new Scanner(System.in);

        List<Parcel> parcels = InputHandler.fillManually(scanner);

        assertEquals(1, parcels.size());
        assertNotNull(parcels.get(0));
    }

    @Test
    void whenFillFromFileWithValidContent_thenOneParcelCreated() throws IOException {
        String fileContent = "COUNTRIES:USA,France\nSTANDARD,John Doe,5.0,12345,10";
        java.nio.file.Files.writeString(java.nio.file.Paths.get("testfile.txt"), fileContent);

        List<Parcel> parcels = InputHandler.fillFromFile("testfile.txt", new Scanner("1\n"));

        assertEquals(1, parcels.size());
        assertNotNull(parcels.get(0));

        java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get("testfile.txt"));
    }

    @Test
    void whenFillRandomlyWithOneParcel_thenOneParcelCreated() {
        List<Parcel> parcels = InputHandler.fillRandomly(new Scanner("1\n"));

        assertEquals(1, parcels.size());
        assertNotNull(parcels.get(0));
    }

    @Test
    void whenReadParcelCountWithValidNumber_thenReturnsTwo() {
        String input = "2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        scanner = new Scanner(System.in);

        int count = InputHandler.readParcelCount(scanner);

        assertEquals(2, count);
    }

    @Test
    void whenReadValidTypeWithStandard_thenReturnsStandard() {
        String input = "STANDARD\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        scanner = new Scanner(System.in);

        String type = InputHandler.readValidType(scanner);

        assertEquals("STANDARD", type);
    }
}