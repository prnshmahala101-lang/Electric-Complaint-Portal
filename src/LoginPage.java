
import javax.swing.*;
import java.awt.*;

public class LoginPage extends JPanel {

    Main app;

    LoginPage(Main app) {

        this.app = app;

        setLayout(new BorderLayout());
        setBackground(new Color(235, 243, 255));

        add(app.createHeader(), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(235, 243, 255));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.setPreferredSize(new Dimension(420, 450));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(190, 215, 245), 2
                ),
                BorderFactory.createEmptyBorder(
                        35, 40, 35, 40
                )
        ));

        JLabel icon = new JLabel("⚡");
        icon.setFont(new Font("Arial", Font.BOLD, 45));
        icon.setForeground(new Color(45, 110, 200));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Electricity Portal");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(new Color(35, 55, 125));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Customer Login");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField username = new JTextField();
        username.setMaximumSize(new Dimension(350, 50));
        username.setFont(new Font("Arial", Font.PLAIN, 15));
        username.setBorder(BorderFactory.createTitledBorder(
                "Username"
        ));

        JPasswordField password = new JPasswordField();
        password.setMaximumSize(new Dimension(350, 50));
        password.setFont(new Font("Arial", Font.PLAIN, 15));
        password.setBorder(BorderFactory.createTitledBorder(
                "Password"
        ));

        JButton login = new JButton("LOGIN");

        login.setMaximumSize(new Dimension(350, 45));
        login.setBackground(new Color(55, 115, 210));
        login.setForeground(Color.WHITE);
        login.setFont(new Font("Arial", Font.BOLD, 16));

        login.setOpaque(true);
        login.setContentAreaFilled(true);
        login.setFocusPainted(false);
        login.setBorderPainted(false);

        JLabel demo = new JLabel("Demo Login: admin / 1234");
        demo.setForeground(Color.GRAY);
        demo.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(icon);
        card.add(Box.createVerticalStrut(10));
        card.add(title);
        card.add(Box.createVerticalStrut(8));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(25));
        card.add(username);
        card.add(Box.createVerticalStrut(15));
        card.add(password);
        card.add(Box.createVerticalStrut(20));
        card.add(login);
        card.add(Box.createVerticalStrut(15));
        card.add(demo);

        center.add(card);

        add(center, BorderLayout.CENTER);

        login.addActionListener(e -> {

            String u = username.getText().trim();
            String p = new String(password.getPassword());

            if (u.equals("admin") && p.equals("1234")) {

                app.username = "Admin";
                app.showPage("Dashboard");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
