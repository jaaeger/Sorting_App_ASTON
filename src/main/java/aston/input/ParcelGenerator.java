package aston.input;

import aston.app.builder.ParcelBuilder;
import aston.app.entity.Parcel;
import aston.app.validation.ParcelValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParcelGenerator {

    private static final Logger logger = Logger.getLogger(ParcelGenerator.class.getName());
    private static final Random random = new Random();

    public static List<Parcel> fillManually(Scanner scanner) {
        List<Parcel> parcels = new ArrayList<>();
        logger.info("Enter number of parcels:");
        int count = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < count; i++) {
            logger.info("Parcel #" + (i + 1));
            System.out.print("Parcel type (STANDARD / EXPRESS / INTERNATIONAL): ");
            String type = scanner.nextLine();

            ParcelBuilder builder = new ParcelBuilder();
            builder.setRecipientName(readNonEmptyString(scanner, "Recipient name: "));
            builder.setWeight(readPositiveDouble(scanner, "Weight: "));
            builder.setTrackingNumber(readPositiveInt(scanner, "Tracking number: "));

            if (type.equalsIgnoreCase("EXPRESS")) {
                builder.setPriorityLevel(readPositiveInt(scanner, "Priority level: "));
            } else if (type.equalsIgnoreCase("INTERNATIONAL")) {
                System.out.print("Country code: ");
                builder.setCountryCode(scanner.nextLine().trim());
            }

            try {
                Parcel parcel = builder.build(type);
                ParcelValidator.validate(parcel);
                parcels.add(parcel);
            } catch (IllegalArgumentException e) {
                logger.log(Level.SEVERE, "Validation failed: " + e.getMessage());
                i--;
            }
        }
        return parcels;
    }

    public static List<Parcel> fillFromFile(String filePath) {
        List<Parcel> parcels = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) {
                    logger.warning("Skipped line (invalid format): " + line);
                    continue;
                }
                String type = parts[0].trim();
                ParcelBuilder builder = new ParcelBuilder()
                        .setRecipientName(parts[1].trim())
                        .setWeight(Double.parseDouble(parts[2].trim()))
                        .setTrackingNumber(Integer.parseInt(parts[3].trim()));

                if (type.equalsIgnoreCase("EXPRESS") && parts.length > 4) {
                    builder.setPriorityLevel(Integer.parseInt(parts[4].trim()));
                } else if (type.equalsIgnoreCase("INTERNATIONAL") && parts.length > 4) {
                    builder.setCountryCode(parts[4].trim());
                }

                try {
                    Parcel parcel = builder.build(type);
                    ParcelValidator.validate(parcel);
                    parcels.add(parcel);
                } catch (IllegalArgumentException e) {
                    logger.log(Level.SEVERE, "Validation failed for line: " + line + " -> " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file: " + e.getMessage());
        }
        return parcels;
    }

    public static List<Parcel> fillRandomly(int count) {
        List<Parcel> parcels = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String type;
            int rnd = random.nextInt(3);
            if (rnd == 0) type = "STANDARD";
            else if (rnd == 1) type = "EXPRESS";
            else type = "INTERNATIONAL";

            ParcelBuilder builder = new ParcelBuilder()
                    .setRecipientName("User" + (i + 1))
                    .setWeight(0.5 + random.nextDouble() * 20)
                    .setTrackingNumber(1000 + random.nextInt(9000));

            if (type.equals("EXPRESS")) {
                builder.setPriorityLevel(1 + random.nextInt(10));
            } else if (type.equals("INTERNATIONAL")) {
                builder.setCountryCode("US");
            }

            Parcel parcel = builder.build(type);
            ParcelValidator.validate(parcel);
            parcels.add(parcel);
        }
        logger.info(count + " random parcels generated.");
        return parcels;
    }

    private static String readNonEmptyString(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            logger.warning("Field cannot be empty, try again.");
        }
    }

    private static double readPositiveDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine();
                if (value > 0) return value;
            } else {
                scanner.nextLine();
            }
            logger.warning("Invalid input, enter a positive number.");
        }
    }

    private static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value > 0) return value;
            } else {
                scanner.nextLine();
            }
            logger.warning("Invalid input, enter a positive integer.");
        }
    }
}
