package pharmasync;

public class TestMultiSale {

    public static void main(String[] args) {

        MultiSaleDAO multiSaleDAO =
                new MultiSaleDAO();

        int[] medicineIds = {1, 5};

        int[] quantities = {5, 2};

        multiSaleDAO.sellMultipleMedicines(
                medicineIds,
                quantities
        );
    }
}