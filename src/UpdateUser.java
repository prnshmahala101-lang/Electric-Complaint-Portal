import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateUser {

    public void update(String oldName, String newName) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "UPDATE users SET username = ? WHERE username = ?"
        );
        ps.setString(1, newName);
        ps.setString(2, oldName);

        int rows = ps.executeUpdate();
        if (rows > 0) {
            System.out.println("Username Updated Successfully from " + oldName + " to " + newName);
        } else {
            System.out.println("Username Not Found");
        }
        ps.close();
        con.close();
    }
}
