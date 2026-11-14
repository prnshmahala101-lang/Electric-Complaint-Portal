import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteComplaint {

    /**
     * Deletes a complaint from the database using its Complaint ID.
     */
    public boolean delete(String complaintId) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "DELETE FROM complaints WHERE complaint_id = ?"
        );
        ps.setString(1, complaintId);

        int rows = ps.executeUpdate();
        if (rows > 0) {
            System.out.println("Complaint " + complaintId + " deleted successfully from database.");
        } else {
            System.out.println("Complaint " + complaintId + " Not Found");
        }

        ps.close();
        con.close();
        return rows > 0;
    }
}
