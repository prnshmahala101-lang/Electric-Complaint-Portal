
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main extends JFrame {

    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    static ArrayList<String[]> complaints = new ArrayList<>();

    String username = "Admin";
    String consumerNo = "1234567890";

    ComplaintStatus statusPage;
    Dashboard dashboard;

    Color navy = new Color(35, 55, 125);
    Color lightBlue = new Color(225, 240, 255);
    Color background = new Color(245, 249, 255);

    Main() {

        setTitle("Electricity Complaint Portal | PIET");
        setSize(1250, 800);
        setMinimumSize(new Dimension(1050, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(background);

        // Initial default complaints
        complaints.add(new String[]{
                "CMP1001", "1234567890",
                "Power Failure", "Pending", "02-11-2026"
        });

        complaints.add(new String[]{
                "CMP1002", "1234567890",
                "Voltage Issue", "Resolved", "01-11-2026"
        });

        // Load complaints from MySQL database if connected
        try {
            SelectComplaint selectObj = new SelectComplaint();
            ArrayList<String[]> dbList = selectObj.selectAll();
            if (dbList != null && !dbList.isEmpty()) {
                complaints = dbList;
            }
        } catch (Exception e) {
            System.err.println("Database startup notice: " + e.getMessage());
        }

        LoginPage loginPage = new LoginPage(this);
        dashboard = new Dashboard(this);
        RegisterComplaint registerPage = new RegisterComplaint(this);
        statusPage = new ComplaintStatus(this);
        ProfilePage profilePage = new ProfilePage(this);

        mainPanel.add(loginPage, "Login");
        mainPanel.add(dashboard, "Dashboard");
        mainPanel.add(registerPage, "Register");
        mainPanel.add(statusPage, "Status");
        mainPanel.add(profilePage, "Profile");

        add(mainPanel);

        showPage("Login");
    }

    void showPage(String page) {

        if (page.equals("Dashboard")) {
            dashboard.refreshDashboard();
        }

        if (page.equals("Status")) {
            statusPage.loadData();
        }

        cardLayout.show(mainPanel, page);
    }

    // COLLEGE LOGO HEADER FOR EVERY PAGE
    JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 125));

        ImageIcon original = new ImageIcon("pietlogo.png");

        Image image = original.getImage().getScaledInstance(
                1150, 110, Image.SCALE_SMOOTH
        );

        JLabel logo = new JLabel(new ImageIcon(image));
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(logo, BorderLayout.CENTER);

        header.setBorder(BorderFactory.createMatteBorder(
                0, 0, 3, 0, new Color(190, 215, 245)
        ));

        return header;
    }

    // LIGHT BLUE SIDEBAR
    JPanel createSidebar() {

        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));

        side.setBackground(new Color(220, 235, 255));
        side.setPreferredSize(new Dimension(220, 0));

        JLabel menuTitle = new JLabel("  MAIN MENU");

        menuTitle.setForeground(navy);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 17));

        menuTitle.setBorder(BorderFactory.createEmptyBorder(
                25, 10, 25, 10
        ));

        side.add(menuTitle);

        String[] names = {
                "Dashboard",
                "Register Complaint",
                "Complaint Status",
                "User Management",
                "My Profile",
                "Logout"
        };

        String[] pages = {
                "Dashboard",
                "Register",
                "Status",
                "UserManagement",
                "Profile",
                "Login"
        };

        for (int i = 0; i < names.length; i++) {

            JButton btn = new JButton(names[i]);

            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setForeground(navy);
            btn.setBackground(new Color(235, 245, 255));

            btn.setMaximumSize(new Dimension(220, 50));
            btn.setPreferredSize(new Dimension(220, 50));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);

            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);

            btn.setBorder(BorderFactory.createEmptyBorder(
                    10, 15, 10, 15
            ));

            String page = pages[i];

            btn.addActionListener(e -> {

                if (page.equals("Login")) {

                    int result = JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to logout?",
                            "Logout",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (result == JOptionPane.YES_OPTION) {
                        showPage("Login");
                    }

                } else if (page.equals("UserManagement")) {
                    new UserPage();
                } else {
                    showPage(page);
                }
            });

            side.add(Box.createVerticalStrut(8));
            side.add(btn);
        }

        side.add(Box.createVerticalGlue());

        return side;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

            new Main().setVisible(true);
        });
    }
}
