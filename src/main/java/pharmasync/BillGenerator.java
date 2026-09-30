package pharmasync;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BillGenerator {

    public void generateBill(
            int billNo,
            List<BillItem> items) {

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        String dateTime = now.format(formatter);

        double grandTotal = 0;

        System.out.println();
        System.out.println("========================================");
        System.out.println("             PHARMASYNC");
        System.out.println("           CUSTOMER BILL");
        System.out.println("========================================");

        System.out.println("Bill No   : " + billNo);
        System.out.println("Date/Time : " + dateTime);

        System.out.println("----------------------------------------");

        System.out.printf(
                "%-22s %-5s %-10s%n",
                "Medicine",
                "Qty",
                "Total"
        );

        System.out.println("----------------------------------------");

        for (BillItem item : items) {

            double itemTotal = item.getTotal();

            grandTotal = grandTotal + itemTotal;

            System.out.printf(
                    "%-22s %-5d Rs.%-8.2f%n",
                    item.getMedicineName(),
                    item.getQuantity(),
                    itemTotal
            );
        }

        System.out.println("----------------------------------------");

        System.out.printf(
                "GRAND TOTAL: Rs.%.2f%n",
                grandTotal
        );

        System.out.println("----------------------------------------");

        System.out.println("          Thank You!");
        System.out.println("          Visit Again");

        System.out.println("========================================");
    }
}