import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertUser {

    public void insert(String username) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users VALUES (?)"
        );
        ps.setString(1, username);
        ps.executeUpdate();
        System.out.println("Username Inserted Successfully: " + username);
        ps.close();
        con.close();
    }
}
