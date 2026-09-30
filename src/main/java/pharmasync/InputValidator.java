package pharmasync;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidator {

    // Check empty text
    public static boolean isEmpty(String input) {

        return input == null || input.trim().isEmpty();
    }

    // Check positive quantity
    public static boolean isPositiveQuantity(int quantity) {

        return quantity > 0;
    }

    // Check positive price
    public static boolean isPositivePrice(double price) {

        return price > 0;
    }

    // Check valid ID
    public static boolean isValidId(int id) {

        return id > 0;
    }

    // Check date format (YYYY-MM-DD)
    public static boolean isValidDate(String date) {

        try {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate.parse(date, formatter);

            return true;

        } catch (DateTimeParseException e) {

            return false;
        }
    }

    // Check expiry date is after manufacture date
    public static boolean isExpiryAfterManufacture(
            String manufactureDate,
            String expiryDate) {

        try {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate mfg =
                    LocalDate.parse(manufactureDate, formatter);

            LocalDate exp =
                    LocalDate.parse(expiryDate, formatter);

            return exp.isAfter(mfg);

        } catch (DateTimeParseException e) {

            return false;
        }
    }
}
