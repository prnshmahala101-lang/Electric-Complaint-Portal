import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteUser {

    public void delete(String username) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "DELETE FROM users WHERE username = ?"
        );
        ps.setString(1, username);

        int rows = ps.executeUpdate();
        if (rows > 0) {
            System.out.println("Username Deleted Successfully: " + username);
        } else {
            System.out.println("Username Not Found");
        }
        ps.close();
        con.close();
    }
}
