
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterComplaint extends JPanel {

    Main app;

    RegisterComplaint(Main app) {

        this.app = app;

        setLayout(new BorderLayout());

        add(app.createHeader(), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(new Color(245, 249, 255));

        body.add(app.createSidebar(), BorderLayout.WEST);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);

        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(200, 220, 245), 2
                ),
                BorderFactory.createEmptyBorder(
                        20, 35, 20, 35
                )
        ));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel title = new JLabel("Register New Complaint");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(new Color(35, 55, 125));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        form.add(title, gbc);

        JTextField consumer = new JTextField();
        consumer.setFont(new Font("Arial", Font.PLAIN, 15));
        consumer.setPreferredSize(new Dimension(350, 40));

        gbc.gridy = 1;
        form.add(createLabel("Consumer Number"), gbc);

        gbc.gridy = 2;
        form.add(consumer, gbc);

        String[] categories = {
                "Power Failure",
                "Voltage Issue",
                "Electricity Bill Issue",
                "Meter Problem",
                "Wire Damage",
                "Other"
        };

        JComboBox<String> category = new JComboBox<>(categories);
        category.setFont(new Font("Arial", Font.PLAIN, 15));

        gbc.gridy = 3;
        form.add(createLabel("Complaint Category"), gbc);

        gbc.gridy = 4;
        form.add(category, gbc);

        JTextField address = new JTextField();
        address.setFont(new Font("Arial", Font.PLAIN, 15));

        gbc.gridy = 5;
        form.add(createLabel("Full Address"), gbc);

        gbc.gridy = 6;
        form.add(address, gbc);

        JTextArea description = new JTextArea(4, 20);
        description.setFont(new Font("Arial", Font.PLAIN, 15));
        description.setLineWrap(true);
        description.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(description);

        gbc.gridy = 7;
        form.add(createLabel("Complaint Description"), gbc);

        gbc.gridy = 8;
        form.add(scroll, gbc);

        JButton submit = new JButton("Submit Complaint");

        submit.setBackground(new Color(65, 125, 210));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Arial", Font.BOLD, 15));

        submit.setOpaque(true);
        submit.setContentAreaFilled(true);
        submit.setFocusPainted(false);
        submit.setBorderPainted(false);

        gbc.gridy = 9;
        form.add(submit, gbc);

        body.add(form, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);

        submit.addActionListener(e -> {

            String consumerNumber = consumer.getText().trim();
            String addr = address.getText().trim();
            String desc = description.getText().trim();

            if (consumerNumber.isEmpty()
                    || addr.isEmpty()
                    || desc.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields!",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String id = "CMP" + (1001 + (int)(System.currentTimeMillis() % 9000));

            String date = new SimpleDateFormat("dd-MM-yyyy")
                    .format(new Date());

            String selectedCategory = category.getSelectedItem().toString();

            // Insert into MySQL Database using separate CRUD class InsertComplaint
            boolean dbSuccess = false;
            try {
                InsertComplaint insertObj = new InsertComplaint();
                dbSuccess = insertObj.insert(id, consumerNumber, selectedCategory, "Pending", date, addr, desc);
            } catch (Exception ex) {
                System.err.println("Database insert notice: " + ex.getMessage());
            }

            // Maintain in-memory list for instant UI display
            Main.complaints.add(0, new String[]{
                    id,
                    consumerNumber,
                    selectedCategory,
                    "Pending",
                    date
            });

            app.statusPage.loadData();
            app.dashboard.refreshDashboard();

            String successMsg = "Complaint submitted successfully!\n\nComplaint ID: " + id;
            if (dbSuccess) {
                successMsg += "\n(Saved to MySQL Database)";
            }

            JOptionPane.showMessageDialog(
                    this,
                    successMsg,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            consumer.setText("");
            address.setText("");
            description.setText("");

            app.showPage("Status");
        });
    }

    JLabel createLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(35, 55, 125));

        return label;
    }
}
