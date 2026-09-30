package pharmasync;

public class TestLogin {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        userDAO.login(
                "admin",
                "admin123"
        );
    }
}