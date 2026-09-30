package pharmasync.dao;

import pharmasync.database.DBConnection;
import pharmasync.model.Medicine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MedicineDAO {

    // =========================================================
    // INSERT MEDICINE
    // =========================================================

    public boolean insertMedicine(Medicine medicine) {

        String sql = "INSERT INTO medicines " +
                "(medicine_name, company, category, quantity, price, expiry_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, medicine.getMedicineName());
            pst.setString(2, medicine.getCompany());
            pst.setString(3, medicine.getCategory());
            pst.setInt(4, medicine.getQuantity());
            pst.setDouble(5, medicine.getPrice());
            pst.setString(6, medicine.getExpiryDate());

            int rows = pst.executeUpdate();

            pst.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // GET ALL MEDICINES
    // =========================================================

    public ArrayList<Medicine> getAllMedicines() {

        ArrayList<Medicine> list = new ArrayList<>();

        String sql = "SELECT * FROM medicines";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Medicine medicine =
                        new Medicine();

                medicine.setMedicineName(
                        rs.getString("medicine_name")
                );

                medicine.setCompany(
                        rs.getString("company")
                );

                medicine.setCategory(
                        rs.getString("category")
                );

                medicine.setQuantity(
                        rs.getInt("quantity")
                );

                medicine.setPrice(
                        rs.getDouble("price")
                );

                medicine.setExpiryDate(
                        rs.getString("expiry_date")
                );

                list.add(medicine);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }


    // =========================================================
    // SEARCH MEDICINE
    // =========================================================

    public ArrayList<Medicine> searchMedicine(
            String keyword) {

        ArrayList<Medicine> list =
                new ArrayList<>();

        String sql =
                "SELECT * FROM medicines " +
                        "WHERE medicine_name LIKE ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(
                    1,
                    "%" + keyword + "%"
            );

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Medicine medicine =
                        new Medicine();

                medicine.setMedicineName(
                        rs.getString("medicine_name")
                );

                medicine.setCompany(
                        rs.getString("company")
                );

                medicine.setCategory(
                        rs.getString("category")
                );

                medicine.setQuantity(
                        rs.getInt("quantity")
                );

                medicine.setPrice(
                        rs.getDouble("price")
                );

                medicine.setExpiryDate(
                        rs.getString("expiry_date")
                );

                list.add(medicine);
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }


    // =========================================================
    // GET MEDICINE BY NAME
    // =========================================================

    public Medicine getMedicineByName(
            String medicineName) {

        Medicine medicine = null;

        String sql =
                "SELECT * FROM medicines " +
                        "WHERE medicine_name = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(
                    1,
                    medicineName
            );

            ResultSet rs =
                    pst.executeQuery();

            if (rs.next()) {

                medicine =
                        new Medicine();

                medicine.setMedicineName(
                        rs.getString("medicine_name")
                );

                medicine.setCompany(
                        rs.getString("company")
                );

                medicine.setCategory(
                        rs.getString("category")
                );

                medicine.setQuantity(
                        rs.getInt("quantity")
                );

                medicine.setPrice(
                        rs.getDouble("price")
                );

                medicine.setExpiryDate(
                        rs.getString("expiry_date")
                );
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return medicine;
    }


    // =========================================================
    // DELETE MEDICINE
    // =========================================================

    public boolean deleteMedicine(
            String medicineName) {

        String sql =
                "DELETE FROM medicines " +
                        "WHERE medicine_name = ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(
                    1,
                    medicineName
            );

            int rows =
                    pst.executeUpdate();

            pst.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // UPDATE MEDICINE
    // =========================================================

    public boolean updateMedicine(
            Medicine medicine) {

        String sql =
                "UPDATE medicines SET " +
                        "company=?, " +
                        "category=?, " +
                        "quantity=?, " +
                        "price=?, " +
                        "expiry_date=? " +
                        "WHERE medicine_name=?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setString(
                    1,
                    medicine.getCompany()
            );

            pst.setString(
                    2,
                    medicine.getCategory()
            );

            pst.setInt(
                    3,
                    medicine.getQuantity()
            );

            pst.setDouble(
                    4,
                    medicine.getPrice()
            );

            pst.setString(
                    5,
                    medicine.getExpiryDate()
            );

            pst.setString(
                    6,
                    medicine.getMedicineName()
            );

            int rows =
                    pst.executeUpdate();

            pst.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // REDUCE STOCK AFTER SALE
    // =========================================================

    public boolean reduceStock(
            String medicineName,
            int quantity) {

        String sql =
                "UPDATE medicines " +
                        "SET quantity = quantity - ? " +
                        "WHERE medicine_name = ? " +
                        "AND quantity >= ?";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setInt(
                    1,
                    quantity
            );

            pst.setString(
                    2,
                    medicineName
            );

            pst.setInt(
                    3,
                    quantity
            );

            int rows =
                    pst.executeUpdate();

            pst.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}