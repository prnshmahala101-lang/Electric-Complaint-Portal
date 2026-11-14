import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertComplaint {

    /**
     * Inserts a new electricity complaint into the MySQL database.
     */
    public boolean insert(String complaintId, String consumerNo, String category,
                          String status, String date, String address, String description) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO complaints (complaint_id, consumer_number, category, status, complaint_date, address, description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"
        );
        ps.setString(1, complaintId);
        ps.setString(2, consumerNo);
        ps.setString(3, category);
        ps.setString(4, status);
        ps.setString(5, date);
        ps.setString(6, address);
        ps.setString(7, description);

        int rows = ps.executeUpdate();
        System.out.println("Complaint " + complaintId + " inserted successfully into database.");

        ps.close();
        con.close();
        return rows > 0;
    }
}
