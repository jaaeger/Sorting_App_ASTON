package ru.aston.team.sortapp.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParcelGenerator {

    private static final Logger logger = Logger.getLogger(ParcelGenerator.class.getName());
    private static final Random random = new Random();

    public static List<Parcel> fillManually(Scanner scanner) {
        List<Parcel> parcels = new ArrayList<>();

        logger.info("Enter the number of parcels:");
        int count = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < count; i++) {
            logger.info("Parcel #" + (i + 1));

            logger.info("Enter recipient name:");
            String name = scanner.nextLine();

            logger.info("Enter weight:");
            double weight = scanner.nextDouble();

            logger.info("Enter tracking number:");
            int track = scanner.nextInt();
            scanner.nextLine();

            try {
                Parcel parcel = new Parcel.Builder()
                        .setRecipientName(name)
                        .setWeight(weight)
                        .setTrackingNumber(track)
                        .build();
                parcels.add(parcel);
            } catch (IllegalArgumentException e) {
                logger.log(Level.SEVERE, "Error: " + e.getMessage());
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
                if (parts.length != 3) {
                    logger.warning("Skipped line (invalid format): " + line);
                    continue;
                }
                try {
                    Parcel parcel = new Parcel.Builder()
                            .setRecipientName(parts[0].trim())
                            .setWeight(Double.parseDouble(parts[1].trim()))
                            .setTrackingNumber(Integer.parseInt(parts[2].trim()))
                            .build();
                    parcels.add(parcel);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error in line '" + line + "': " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file " + filePath + ": " + e.getMessage());
        }

        return parcels;
    }

    public static List<Parcel> fillRandomly(int count) {
        List<Parcel> parcels = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String name = "User" + (i + 1);
            double weight = 0.5 + random.nextDouble() * 20;
            int trackingNumber = 1000 + random.nextInt(9000);

            parcels.add(new Parcel.Builder()
                    .setRecipientName(name)
                    .setWeight(weight)
                    .setTrackingNumber(trackingNumber)
                    .build());
        }

        logger.info(count + " random parcels generated successfully.");
        return parcels;
    }
}
