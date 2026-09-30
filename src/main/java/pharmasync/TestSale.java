package pharmasync;

public class TestSale {

    public static void main(String[] args) {

        SaleDAO saleDAO = new SaleDAO();

        saleDAO.sellMedicine(1, 5);
    }
}