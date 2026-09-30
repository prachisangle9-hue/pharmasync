package pharmasync.dao;

import pharmasync.database.DBConnection;
import pharmasync.model.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SaleDAO {

    // =========================================================
    // INSERT SALE
    // =========================================================

    public boolean insertSale(Sale sale) {

        String sql =
                "INSERT INTO sales " +
                        "(medicine_id, quantity_sold, total_amount) " +
                        "SELECT medicine_id, ?, ? " +
                        "FROM medicines " +
                        "WHERE medicine_name = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            pst.setInt(
                    1,
                    sale.getQuantity()
            );

            pst.setDouble(
                    2,
                    sale.getTotalAmount()
            );

            pst.setString(
                    3,
                    sale.getMedicineName()
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
    // GET ALL SALES
    // =========================================================

    public ArrayList<Sale> getAllSales() {

        ArrayList<Sale> list =
                new ArrayList<>();

        String sql =
                "SELECT " +
                        "s.sale_id, " +
                        "m.medicine_name, " +
                        "s.quantity_sold, " +
                        "CASE " +
                        "WHEN s.quantity_sold > 0 " +
                        "THEN s.total_amount / s.quantity_sold " +
                        "ELSE 0 " +
                        "END AS price, " +
                        "s.total_amount, " +
                        "s.sale_date " +
                        "FROM sales s " +
                        "INNER JOIN medicines m " +
                        "ON s.medicine_id = m.medicine_id " +
                        "ORDER BY s.sale_id DESC";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                Sale sale =
                        new Sale();

                sale.setSaleId(
                        rs.getInt("sale_id")
                );

                sale.setMedicineName(
                        rs.getString("medicine_name")
                );

                sale.setQuantity(
                        rs.getInt("quantity_sold")
                );

                sale.setPrice(
                        rs.getDouble("price")
                );

                sale.setTotalAmount(
                        rs.getDouble("total_amount")
                );

                sale.setSaleDate(
                        rs.getString("sale_date")
                );

                list.add(sale);
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
    // GET TOTAL SALES AMOUNT
    // =========================================================

    public double getTotalSalesAmount() {

        String sql =
                "SELECT COALESCE(SUM(total_amount), 0) " +
                        "AS total_sales FROM sales";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            double total = 0;

            if (rs.next()) {

                total =
                        rs.getDouble("total_sales");
            }

            rs.close();
            pst.close();
            con.close();

            return total;

        } catch (Exception e) {

            e.printStackTrace();

            return 0;
        }
    }


    // =========================================================
    // GET TOTAL NUMBER OF SALES
    // =========================================================

    public int getTotalSalesCount() {

        String sql =
                "SELECT COUNT(*) AS total_count " +
                        "FROM sales";

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            int count = 0;

            if (rs.next()) {

                count =
                        rs.getInt("total_count");
            }

            rs.close();
            pst.close();
            con.close();

            return count;

        } catch (Exception e) {

            e.printStackTrace();

            return 0;
        }
    }
}