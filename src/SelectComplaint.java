import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SelectComplaint {

    /**
     * Prints a complaint's details to the console (demonstrating console SELECT).
     */
    public void select(String complaintId) throws Exception {
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM complaints WHERE complaint_id = ?"
        );
        ps.setString(1, complaintId);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("Complaint ID: " + rs.getString("complaint_id"));
            System.out.println("Consumer No:  " + rs.getString("consumer_number"));
            System.out.println("Category:     " + rs.getString("category"));
            System.out.println("Status:       " + rs.getString("status"));
            System.out.println("Date:         " + rs.getString("complaint_date"));
            System.out.println("Address:      " + rs.getString("address"));
            System.out.println("Description:  " + rs.getString("description"));
        } else {
            System.out.println("Complaint ID " + complaintId + " Not Found");
        }
        rs.close();
        ps.close();
        con.close();
    }

    /**
     * Retrieves all complaints from the database for the ComplaintStatus table.
     */
    public ArrayList<String[]> selectAll() throws Exception {
        ArrayList<String[]> list = new ArrayList<>();
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT complaint_id, consumer_number, category, status, complaint_date FROM complaints ORDER BY complaint_id DESC"
        );

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new String[]{
                    rs.getString("complaint_id"),
                    rs.getString("consumer_number"),
                    rs.getString("category"),
                    rs.getString("status"),
                    rs.getString("complaint_date")
            });
        }

        rs.close();
        ps.close();
        con.close();
        return list;
    }

    /**
     * Retrieves statistical counts [total, pending, resolved] for the dashboard.
     */
    public int[] getCounts() throws Exception {
        int total = 0, pending = 0, resolved = 0;
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT status, COUNT(*) as cnt FROM complaints GROUP BY status"
        );

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String status = rs.getString("status");
            int count = rs.getInt("cnt");
            total += count;
            if ("Pending".equalsIgnoreCase(status)) {
                pending += count;
            } else if ("Resolved".equalsIgnoreCase(status)) {
                resolved += count;
            }
        }

        rs.close();
        ps.close();
        con.close();
        return new int[]{total, pending, resolved};
    }
}
