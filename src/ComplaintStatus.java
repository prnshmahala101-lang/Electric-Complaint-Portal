import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ComplaintStatus extends JPanel {

    Main app;
    DefaultTableModel model;
    JTable table;

    ComplaintStatus(Main app) {
        this.app = app;

        setLayout(new BorderLayout());
        add(app.createHeader(), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(new Color(245, 249, 255));
        body.add(app.createSidebar(), BorderLayout.WEST);

        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBackground(new Color(245, 249, 255));
        content.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // Header Panel: Title and Action Buttons (Refresh, Update Status, Delete)
        JLabel title = new JLabel("Complaint Status");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(35, 55, 125));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton refresh = new JButton("Refresh Table");
        styleActionButton(refresh, new Color(215, 235, 255), new Color(35, 55, 125));

        JButton updateStatusBtn = new JButton("Toggle Status");
        styleActionButton(updateStatusBtn, new Color(225, 245, 230), new Color(25, 120, 60));

        JButton deleteBtn = new JButton("Delete Complaint");
        styleActionButton(deleteBtn, new Color(255, 230, 230), new Color(180, 40, 40));

        buttonPanel.add(updateStatusBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refresh);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(title, BorderLayout.WEST);
        header.add(buttonPanel, BorderLayout.EAST);

        String[] columns = {
                "Complaint ID",
                "Consumer Number",
                "Category",
                "Status",
                "Date"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(210, 230, 250));
        table.getTableHeader().setForeground(new Color(35, 55, 125));
        table.setSelectionBackground(new Color(190, 220, 250));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(table);

        // Action: Refresh
        refresh.addActionListener(e -> loadData());

        // Action: Toggle/Update Status using UpdateComplaint class
        updateStatusBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Please select a complaint row from the table first.",
                        "No Complaint Selected",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String complaintId = model.getValueAt(selectedRow, 0).toString();
            String currentStatus = model.getValueAt(selectedRow, 3).toString();
            String newStatus = "Pending".equalsIgnoreCase(currentStatus) ? "Resolved" : "Pending";

            try {
                // Call separate CRUD class UpdateComplaint
                UpdateComplaint updateObj = new UpdateComplaint();
                updateObj.updateStatus(complaintId, newStatus);
            } catch (Exception ex) {
                System.err.println("DB Update notice: " + ex.getMessage());
            }

            // Update in-memory cache and table model
            model.setValueAt(newStatus, selectedRow, 3);
            for (String[] c : Main.complaints) {
                if (c[0].equals(complaintId)) {
                    c[3] = newStatus;
                    break;
                }
            }

            app.dashboard.refreshDashboard();
            JOptionPane.showMessageDialog(this,
                    "Complaint " + complaintId + " status updated to: " + newStatus,
                    "Status Updated",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Action: Delete Complaint using DeleteComplaint class
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Please select a complaint row from the table first.",
                        "No Complaint Selected",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String complaintId = model.getValueAt(selectedRow, 0).toString();

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete complaint " + complaintId + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Call separate CRUD class DeleteComplaint
                    DeleteComplaint deleteObj = new DeleteComplaint();
                    deleteObj.delete(complaintId);
                } catch (Exception ex) {
                    System.err.println("DB Delete notice: " + ex.getMessage());
                }

                // Remove from in-memory cache
                Main.complaints.removeIf(c -> c[0].equals(complaintId));
                model.removeRow(selectedRow);

                app.dashboard.refreshDashboard();
                JOptionPane.showMessageDialog(this,
                        "Complaint " + complaintId + " deleted successfully.",
                        "Deleted",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        content.add(header, BorderLayout.NORTH);
        content.add(scroll, BorderLayout.CENTER);

        body.add(content, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);

        loadData();
    }

    private void styleActionButton(JButton btn, Color bg, Color fg) {
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(fg, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }

    /**
     * Loads complaint data using separate CRUD class SelectComplaint.
     */
    void loadData() {
        model.setRowCount(0);

        try {
            SelectComplaint selectObj = new SelectComplaint();
            ArrayList<String[]> dbList = selectObj.selectAll();
            if (dbList != null && !dbList.isEmpty()) {
                Main.complaints = dbList;
            }
        } catch (Exception e) {
            System.err.println("DB Select notice: " + e.getMessage());
        }

        for (String[] c : Main.complaints) {
            model.addRow(new Object[]{
                    c[0], c[1], c[2], c[3], c[4]
            });
        }
    }
}
