import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SelectUser {

    public void select(String username) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username = ?"
        );
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("Username: " + rs.getString("username"));
        } else {
            System.out.println("Username Not Found");
        }
        rs.close();
        ps.close();
        con.close();
    }

    public boolean exists(String username) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT 1 FROM users WHERE username = ?"
        );
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        boolean found = rs.next();
        rs.close();
        ps.close();
        con.close();
        return found;
    }
}
