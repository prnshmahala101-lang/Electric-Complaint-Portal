import java.awt.*;
import java.awt.event.*;

public class UserPage extends Frame implements ActionListener {

    TextField txtUsername;
    Button btnInsert, btnSelect, btnUpdate, btnDelete;
    String oldName = ""; // Purana username store karne ke liye variable

    public UserPage() {
        setTitle("User Management");
        setSize(400, 250);
        setLayout(new FlowLayout());

        Label lbl = new Label("Username:");
        txtUsername = new TextField(20);

        btnInsert = new Button("Insert");
        btnSelect = new Button("Select");
        btnUpdate = new Button("Update");
        btnDelete = new Button("Delete");

        add(lbl);
        add(txtUsername);
        add(btnInsert);
        add(btnSelect);
        add(btnUpdate);
        add(btnDelete);

        btnInsert.addActionListener(this);
        btnSelect.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);

        // Window close handler for AWT Frame
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = txtUsername.getText().trim();

        try {
            if (e.getSource() == btnInsert) {
                if (!name.isEmpty()) {
                    InsertUser obj = new InsertUser();
                    obj.insert(name);
                    txtUsername.setText("");
                } else {
                    System.out.println("Please enter a username to insert");
                }
            } else if (e.getSource() == btnSelect) {
                if (!name.isEmpty()) {
                    SelectUser obj = new SelectUser();
                    obj.select(name);
                    // Purana username store hoga update ke liye
                    oldName = name;
                } else {
                    System.out.println("Please enter a username to select");
                }
            } else if (e.getSource() == btnUpdate) {
                if (!name.isEmpty() && !oldName.isEmpty()) {
                    UpdateUser obj = new UpdateUser();
                    // oldName = purana username, name = textbox ka naya username
                    obj.update(oldName, name);
                    oldName = "";
                    txtUsername.setText("");
                } else {
                    System.out.println("First select a username, then enter the new name to update");
                }
            } else if (e.getSource() == btnDelete) {
                if (!name.isEmpty()) {
                    DeleteUser obj = new DeleteUser();
                    obj.delete(name);
                    txtUsername.setText("");
                } else {
                    System.out.println("Please enter a username to delete");
                }
            }
        } catch (Exception ex) {
            System.err.println("Database Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UserPage();
    }
}
