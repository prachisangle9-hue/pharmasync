package pharmasync;

public class TestDashboard {

    public static void main(String[] args) {

        DashboardDAO dashboardDAO =
                new DashboardDAO();

        dashboardDAO.showDashboard();
    }
}
