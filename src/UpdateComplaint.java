import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateComplaint {

    /**
     * Updates the status of an existing electricity complaint (e.g., Pending -> Resolved).
     */
    public boolean updateStatus(String complaintId, String newStatus) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "UPDATE complaints SET status = ? WHERE complaint_id = ?"
        );
        ps.setString(1, newStatus);
        ps.setString(2, complaintId);

        int rows = ps.executeUpdate();
        if (rows > 0) {
            System.out.println("Complaint " + complaintId + " status updated to: " + newStatus);
        } else {
            System.out.println("Complaint " + complaintId + " Not Found");
        }

        ps.close();
        con.close();
        return rows > 0;
    }
}
