package pharmasync.model;

public class Sale {

    private int saleId;

    private String medicineName;

    private int quantity;

    private double price;

    private double totalAmount;

    private String saleDate;


    // ==========================
    // Default Constructor
    // ==========================

    public Sale() {
    }


    // ==========================
    // Parameterized Constructor
    // ==========================

    public Sale(
            int saleId,
            String medicineName,
            int quantity,
            double price,
            double totalAmount,
            String saleDate
    ) {

        this.saleId = saleId;

        this.medicineName = medicineName;

        this.quantity = quantity;

        this.price = price;

        this.totalAmount = totalAmount;

        this.saleDate = saleDate;
    }


    // ==========================
    // Sale ID
    // ==========================

    public int getSaleId() {

        return saleId;
    }

    public void setSaleId(int saleId) {

        this.saleId = saleId;
    }


    // ==========================
    // Medicine Name
    // ==========================

    public String getMedicineName() {

        return medicineName;
    }

    public void setMedicineName(String medicineName) {

        this.medicineName = medicineName;
    }


    // ==========================
    // Quantity
    // ==========================

    public int getQuantity() {

        return quantity;
    }

    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }


    // ==========================
    // Price
    // ==========================

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {

        this.price = price;
    }


    // ==========================
    // Total Amount
    // ==========================

    public double getTotalAmount() {

        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {

        this.totalAmount = totalAmount;
    }


    // ==========================
    // Sale Date
    // ==========================

    public String getSaleDate() {

        return saleDate;
    }

    public void setSaleDate(String saleDate) {

        this.saleDate = saleDate;
    }
}