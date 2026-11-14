
import javax.swing.*;
import java.awt.*;

public class Dashboard extends JPanel {

    Main app;

    JLabel totalLabel;
    JLabel pendingLabel;
    JLabel resolvedLabel;

    Dashboard(Main app) {

        this.app = app;

        setLayout(new BorderLayout());

        add(app.createHeader(), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(new Color(245, 249, 255));

        body.add(app.createSidebar(), BorderLayout.WEST);

        JPanel content = new JPanel(new BorderLayout(25, 30));
        content.setBackground(new Color(245, 249, 255));
        content.setBorder(BorderFactory.createEmptyBorder(
                30, 35, 30, 35
        ));

        JLabel title = new JLabel("Dashboard Overview");
        title.setFont(new Font("Arial", Font.BOLD, 29));
        title.setForeground(new Color(35, 55, 125));

        JLabel subtitle = new JLabel(
                "Welcome to Electricity Complaint Portal"
        );

        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setForeground(new Color(100, 110, 130));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);

        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);

        JPanel stats = new JPanel(new GridLayout(1, 3, 20, 20));
        stats.setOpaque(false);

        totalLabel = new JLabel("0");
        pendingLabel = new JLabel("0");
        resolvedLabel = new JLabel("0");

        stats.add(createCard(
                "TOTAL COMPLAINTS",
                totalLabel,
                new Color(80, 145, 225),
                new Color(230, 242, 255)
        ));

        stats.add(createCard(
                "PENDING COMPLAINTS",
                pendingLabel,
                new Color(220, 155, 50),
                new Color(255, 246, 225)
        ));

        stats.add(createCard(
                "RESOLVED COMPLAINTS",
                resolvedLabel,
                new Color(40, 155, 115),
                new Color(225, 248, 240)
        ));

        JPanel actions = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 15, 15)
        );

        actions.setOpaque(false);

        JButton register = new JButton("+ Register New Complaint");
        styleButton(register);

        JButton status = new JButton("View Complaint Status");
        styleButton(status);

        register.addActionListener(e -> app.showPage("Register"));
        status.addActionListener(e -> app.showPage("Status"));

        actions.add(register);
        actions.add(status);

        content.add(header, BorderLayout.NORTH);
        content.add(stats, BorderLayout.CENTER);
        content.add(actions, BorderLayout.SOUTH);

        body.add(content, BorderLayout.CENTER);

        add(body, BorderLayout.CENTER);

        refreshDashboard();
    }

    JPanel createCard(
            String text,
            JLabel count,
            Color color,
            Color lightColor
    ) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.setBackground(lightColor);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(
                        25, 20, 25, 20
                )
        ));

        count.setFont(new Font("Arial", Font.BOLD, 40));
        count.setForeground(color);
        count.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(new Color(45, 55, 85));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(count);
        card.add(Box.createVerticalStrut(15));
        card.add(label);
        card.add(Box.createVerticalGlue());

        return card;
    }

    void styleButton(JButton btn) {

        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(new Color(65, 125, 210));
        btn.setForeground(Color.WHITE);

        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        btn.setBorder(BorderFactory.createEmptyBorder(
                12, 20, 12, 20
        ));
    }

    void refreshDashboard() {

        int total = 0;
        int pending = 0;
        int resolved = 0;

        boolean fetchedFromDB = false;
        try {
            SelectComplaint selectObj = new SelectComplaint();
            int[] counts = selectObj.getCounts();
            if (counts != null && (counts[0] > 0 || counts[1] > 0 || counts[2] > 0)) {
                total = counts[0];
                pending = counts[1];
                resolved = counts[2];
                fetchedFromDB = true;
            }
        } catch (Exception e) {
            // DB not connected, fallback to cache
        }

        if (!fetchedFromDB) {
            total = Main.complaints.size();
            for (String[] c : Main.complaints) {
                if ("Pending".equalsIgnoreCase(c[3])) pending++;
                if ("Resolved".equalsIgnoreCase(c[3])) resolved++;
            }
        }

        totalLabel.setText(String.valueOf(total));
        pendingLabel.setText(String.valueOf(pending));
        resolvedLabel.setText(String.valueOf(resolved));
    }
}
