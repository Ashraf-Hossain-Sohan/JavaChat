import view.ChatFrame;
import view.Registration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Login {

    private static final String FILE_PATH = "users.txt";

    ChatFrame cf;

    void createChat(){
         cf = new ChatFrame(true);

    }
    void setvalue(){
        cf.setVisible();
    }


    Login() {



        JFrame frame = new JFrame("X Convo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1070, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        //! Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        //! Load the image
        ImageIcon imageIcon = new ImageIcon("b55.png");

        //! Create a label and set the icon
        JLabel label = new JLabel();
        label.setIcon(imageIcon);
        label.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());

        //! Create a panel for the login form
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        //! Make the panel transparent
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        //! Add components to the panel
        JLabel usernameLabel = new JLabel("First Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        JButton regButton = new JButton("Register");
        gbc.gridx = 1;
        panel.add(regButton, gbc);

        //? Add the label and panel to the layered pane
        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(panel, JLayeredPane.PALETTE_LAYER);

        //? Add the layered pane to the frame
        frame.add(layeredPane, BorderLayout.CENTER);

        //? Add component listener to make the panel responsive
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int frameWidth = frame.getWidth();
                int frameHeight = frame.getHeight();

                //? Calculate new bounds for the panel to keep it centered and responsive
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

        //? Add ActionListener to the register button
        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registration ob = new Registration();
            }
        });

        //? Add ActionListener to the login button



        //! Set the initial size of the panel
        frame.dispatchEvent(new ComponentEvent(frame, ComponentEvent.COMPONENT_RESIZED));

        //! Set the frame visibility
        frame.setVisible(true);




        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (checkCredentials(firstName, password)) {
//                    JOptionPane.showMessageDialog(frame, "Login Success");
                    frame.setVisible(false);
                    frame.dispose();

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            createChat();
                            setvalue();
                        }
                    });


                    // Close the login frame after successful login
                } else {
                    JOptionPane.showMessageDialog(frame, "Password Incorrect");
                }
            }
        });
    }
    private static boolean checkCredentials(String firstName, String password) {
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] credentials = line.split(",");
                if (credentials.length == 4) {
                    if (credentials[0].equals(firstName) && credentials[3].equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
