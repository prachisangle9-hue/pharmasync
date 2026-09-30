package pharmasync;

import java.util.ArrayList;
import java.util.List;

public class TestBill {

    public static void main(String[] args) {

        List<BillItem> items = new ArrayList<>();

        items.add(
                new BillItem(
                        "Paracetamol 500mg",
                        5,
                        2.50
                )
        );

        items.add(
                new BillItem(
                        "Amoxicillin 500mg",
                        2,
                        5.00
                )
        );

        BillGenerator billGenerator =
                new BillGenerator();

        billGenerator.generateBill(
                1004,
                items
        );
    }
}
