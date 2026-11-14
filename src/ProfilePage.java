
import javax.swing.*;
import java.awt.*;

public class ProfilePage extends JPanel {

    Main app;

    ProfilePage(Main app) {

        this.app = app;

        setLayout(new BorderLayout());

        add(app.createHeader(), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(new Color(245, 249, 255));

        body.add(app.createSidebar(), BorderLayout.WEST);

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(new Color(245, 249, 255));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(550, 520));

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(200, 220, 245), 2
                ),
                BorderFactory.createEmptyBorder(
                        30, 40, 30, 40
                )
        ));

        JLabel title = new JLabel("Owner Profile");

        title.setFont(new Font("Arial", Font.BOLD, 27));
        title.setForeground(new Color(35, 55, 125));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel avatar = new JLabel("👤");
        avatar.setFont(new Font("Arial", Font.PLAIN, 60));
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel name = createInfo("Owner Name: Priyanshu");

        JLabel consumer = createInfo(
                "Consumer Number: 1234567890"
        );

        JLabel connection = createInfo(
                "Connection ID: ELEC-2026-001"
        );

        JLabel email = createInfo(
                "Email: owner@gmail.com"
        );

        JLabel mobile = createInfo(
                "Mobile Number: +91 9876543210"
        );

        JLabel address = createInfo(
                "Address: Jaipur, Rajasthan"
        );

        JLabel type = createInfo(
                "Connection Type: Domestic"
        );

        JButton logout = new JButton("Logout");

        logout.setFont(new Font("Arial", Font.BOLD, 15));
        logout.setBackground(new Color(65, 125, 210));
        logout.setForeground(Color.WHITE);

        logout.setOpaque(true);
        logout.setContentAreaFilled(true);
        logout.setFocusPainted(false);
        logout.setBorderPainted(false);

        logout.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.setMaximumSize(new Dimension(350, 45));

        logout.addActionListener(e -> {

            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                app.showPage("Login");
            }
        });

        card.add(title);
        card.add(Box.createVerticalStrut(15));
        card.add(avatar);
        card.add(Box.createVerticalStrut(20));

        card.add(name);
        card.add(Box.createVerticalStrut(12));

        card.add(consumer);
        card.add(Box.createVerticalStrut(12));

        card.add(connection);
        card.add(Box.createVerticalStrut(12));

        card.add(email);
        card.add(Box.createVerticalStrut(12));

        card.add(mobile);
        card.add(Box.createVerticalStrut(12));

        card.add(address);
        card.add(Box.createVerticalStrut(12));

        card.add(type);
        card.add(Box.createVerticalStrut(20));

        card.add(logout);

        content.add(card);
        body.add(content, BorderLayout.CENTER);

        add(body, BorderLayout.CENTER);
    }

    JLabel createInfo(String text) {

        JLabel label = new JLabel(text);

        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setForeground(new Color(45, 60, 90));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        return label;
    }
}
