package pharmasync;

public class TestMedicine {

    public static void main(String[] args) {

        MedicineDAO medicineDAO = new MedicineDAO();

        medicineDAO.checkLowStockAlerts();
    }
}