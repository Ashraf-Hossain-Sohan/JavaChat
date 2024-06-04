package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Registration {
    private static final String FILE_PATH = "users.txt";

    public Registration() {
        //! Create the registration frame
        JFrame regFrame = new JFrame();
        regFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        regFrame.setSize(1070, 600);
        regFrame.setLocationRelativeTo(null);
        regFrame.setLayout(new BorderLayout());

        //! Create a layered pane for the registration frame
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        ImageIcon imageIcon = new ImageIcon("b55.png");

        //! Create a label and set the same background image
        JLabel label = new JLabel();
        label.setIcon(imageIcon);
        label.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());

        //? Create a panel for the registration form
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        //? Make the panel transparent
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        //? Add components to the registration panel
        JLabel firstNameLabel = new JLabel("First Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(firstNameLabel, gbc);

        JTextField firstNameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(firstNameField, gbc);

        JLabel lastNameLabel = new JLabel("Last Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lastNameLabel, gbc);

        JTextField lastNameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(lastNameField, gbc);

        JLabel phoneLabel = new JLabel("Phone Number:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(phoneLabel, gbc);

        JTextField phoneField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        JLabel setPasswordLabel = new JLabel("Set Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(setPasswordLabel, gbc);

        JPasswordField setPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(setPasswordField, gbc);

        JButton submitButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(submitButton, gbc);

        JButton cancelButton = new JButton("Cancel");
        gbc.gridx = 1;
        panel.add(cancelButton, gbc);

        //? Add the label and panel to the layered pane
        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(panel, JLayeredPane.PALETTE_LAYER);

        //? Add the layered pane to the registration frame
        regFrame.add(layeredPane, BorderLayout.CENTER);

        //? Add component listener to make the panel responsive
        regFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int frameWidth = regFrame.getWidth();
                int frameHeight = regFrame.getHeight();

                int panelWidth = Math.min(380, frameWidth - 100);
                int panelHeight = Math.min(400, frameHeight - 100);
                int panelX = (frameWidth - panelWidth) / 2;
                int panelY = (frameHeight - panelHeight) / 2;

                panel.setBounds(panelX, panelY, panelWidth, panelHeight);
                layeredPane.setPreferredSize(new Dimension(frameWidth, frameHeight));
                layeredPane.revalidate();
                layeredPane.repaint();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regFrame.dispose();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String phone = phoneField.getText();
                String password = new String(setPasswordField.getPassword());
                saveRegistrationData(firstName, lastName, phone, password);
                JOptionPane.showMessageDialog(regFrame, "view.Registration Success");
                regFrame.dispose();
            }
        });

        regFrame.setVisible(true);
    }
    private static void saveRegistrationData(String firstName, String lastName, String phone, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(firstName + "," + lastName + "," + phone + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}